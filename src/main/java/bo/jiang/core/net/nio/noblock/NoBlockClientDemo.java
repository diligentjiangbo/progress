package bo.jiang.core.net.nio.noblock;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

/**
 * @author shumpert.jiang
 * @date 2017/1/4 0004 19:45
 */
public class NoBlockClientDemo {
  private static Logger logger = LogManager.getLogger(NoBlockClientDemo.class);
  private static final int PORT = 8000;
  private ByteBuffer sendBuf = ByteBuffer.allocate(1024);
  private ByteBuffer receiveBuf = ByteBuffer.allocate(1024);
  private Charset charset = Charset.forName("GBK");
  private SocketChannel socketChannel;
  private Selector selector;

  public NoBlockClientDemo() throws IOException {
    selector = Selector.open();
    socketChannel = SocketChannel.open();
    socketChannel.connect(new InetSocketAddress(InetAddress.getLocalHost(), PORT));
    logger.info("连接成功");
    socketChannel.configureBlocking(false);
  }

  public void start() throws IOException {
    socketChannel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
    while (selector.select() > 0) {
      Set<SelectionKey> set = selector.selectedKeys();
      Iterator<SelectionKey> it = set.iterator();
      while (it.hasNext()) {
        SelectionKey key = null;
        try {
          key = (SelectionKey)it.next();
          it.remove();
          if (key.isReadable()) {
            receive(key);
          } else if (key.isWritable()) {
            send(key);
          }
        } catch (IOException e) {
          key.cancel();
          key.channel().close();
        }

      }
    }
  }

  public void send(SelectionKey key) throws IOException {
    //logger.info("准备写数据");
    SocketChannel socketChannel = (SocketChannel)key.channel();
    synchronized (sendBuf) {
      sendBuf.flip();
      while (sendBuf.hasRemaining()) {
        socketChannel.write(sendBuf);
      }
      sendBuf.compact();
    }
  }

  public void receive(SelectionKey key) throws IOException {
    logger.info("准备收数据");
    SocketChannel socketChannel = (SocketChannel)key.channel();
    socketChannel.read(receiveBuf);
    receiveBuf.flip();
    String str = decode(receiveBuf);
    if (str.indexOf('\n') == -1) {
      return;
    }
    String s = str.substring(0, str.indexOf('\n') + 1);
    logger.info("收到消息:{}", s);

    if ("BYE\r\n".equals(s)) {
      key.cancel();
      socketChannel.close();
      selector.close();
      logger.info("连接关闭");
      System.exit(0);
    }

    ByteBuffer temp = encode(s);
    receiveBuf.position(temp.limit());
    receiveBuf.compact();

  }

  public void receiveFromConsole() throws IOException {
    logger.info("准备接收控制台消息");
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    String str = null;
    while ((str = br.readLine()) != null) {
      logger.info("收到控制台消息:{}", str);
      synchronized (sendBuf) {
        sendBuf.put(encode(str + "\r\n"));
      }
      if ("bye".equals(str)) {
        break;
      }
    }
  }

  public ByteBuffer encode(String str) {
    return charset.encode(str);
  }

  public String decode(ByteBuffer buf) {
    CharBuffer charBuffer = charset.decode(buf);
    return charBuffer.toString();
  }

  public static void main(String[] args) throws IOException {
    final NoBlockClientDemo noBlockClientDemo = new NoBlockClientDemo();

    Thread thread = new Thread() {
      public void run() {
        try {
          noBlockClientDemo.receiveFromConsole();
        } catch (IOException e) {
          logger.error("异常", e);
        }
      }
    };
    thread.start();
    noBlockClientDemo.start();
  }
}

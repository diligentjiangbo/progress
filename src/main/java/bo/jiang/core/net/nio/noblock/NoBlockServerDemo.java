package bo.jiang.core.net.nio.noblock;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

/**
 * @author shumpert.jiang
 * @date 2017/1/4 0004 20:07
 *
 * 初步猜想同时注册读写事件会导致
 */
public class NoBlockServerDemo {
  private static Logger logger = LogManager.getLogger(NoBlockServerDemo.class);
  private static final int PORT = 8000;
  private ByteBuffer sendBuf = ByteBuffer.allocate(1024);
  private ByteBuffer receiveBuf = ByteBuffer.allocate(1024);
  private Charset charset = Charset.forName("GBK");
  private ServerSocketChannel serverSocketChannel;
  private Selector selector;
  private Object object = new Object();

  public NoBlockServerDemo() throws IOException {
    selector = Selector.open();
    serverSocketChannel = ServerSocketChannel.open();
    serverSocketChannel.bind(new InetSocketAddress(PORT));
  }

  public void service() throws IOException {
    while (true) {
      synchronized (object) {}
      int n = selector.select();
      if (n == 0) continue;
      Set<SelectionKey> set = selector.selectedKeys();
      Iterator<SelectionKey> it = set.iterator();
      while (it.hasNext()) {
        SelectionKey key = it.next();
        it.remove();
        if (key.isWritable()) {
          send(key);
        } else if (key.isReadable()) {
          receive(key);
        }
      }
    }

  }

  public void accept() throws IOException {
    while (true) {
      SocketChannel socketChannel = serverSocketChannel.accept();
      logger.info("建立连接{}", socketChannel.getLocalAddress());
      socketChannel.configureBlocking(false);
      ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
      synchronized (object) {
        selector.wakeup();
        socketChannel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE, byteBuffer);
      }
    }
  }

  public void send(SelectionKey key) throws IOException {
    SocketChannel socketChannel = (SocketChannel) key.channel();
    ByteBuffer byteBuffer = (ByteBuffer) key.attachment();
    byteBuffer.flip();
    String s = decode(byteBuffer);
    if (s.indexOf("\n") == -1) {
      return;
    }
    String str = s.substring(0, s.indexOf("\n") + 1);
    logger.info("收到消息:{}", str);
    ByteBuffer temp = encode(str.toUpperCase());
    //socketChannel.write(temp);
    while (temp.hasRemaining()) {
      socketChannel.write(temp);
    }
    if ("bye\r\n".equals(str)) {
      socketChannel.close();
    }
    byteBuffer.position(temp.limit());
    byteBuffer.compact();
  }

  public void receive(SelectionKey key) throws IOException {
    SocketChannel socketChannel = (SocketChannel) key.channel();
    ByteBuffer byteBuffer = (ByteBuffer) key.attachment();
    byteBuffer.limit(byteBuffer.capacity());
    socketChannel.read(byteBuffer);
  }

  public ByteBuffer encode(String s) {
    return charset.encode(s);
  }

  public String decode(ByteBuffer buf) {
    CharBuffer charBuffer = charset.decode(buf);
    return charBuffer.toString();
  }

  public static void main(String[] args) throws IOException {
    final NoBlockServerDemo noBlockServerDemo = new NoBlockServerDemo();
    Thread thread = new Thread() {
      public void run() {
        try {
          noBlockServerDemo.accept();
        } catch (IOException e) {
          logger.error("异常", e);
        }
      }
    };

    thread.start();
    noBlockServerDemo.service();
  }
}

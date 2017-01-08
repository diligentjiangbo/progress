package bo.jiang.core.net.nio.simple;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

/**
 * @author shumpert.jiang
 * @date 2017/1/6 0006 11:52
 */
public class Server {
  private static Logger logger = LogManager.getLogger(Server.class);
  private ServerSocketChannel serverSocketChannel;
  private Charset charset = Charset.forName("GBK");
  private Selector selector;
  private static final int PORT = 8000;

  public Server() throws IOException {
    selector = Selector.open();
    serverSocketChannel = ServerSocketChannel.open();
    serverSocketChannel.bind(new InetSocketAddress(PORT));
    serverSocketChannel.configureBlocking(false);
    serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
  }

  public void service() throws IOException {
    while (selector.select() > 0) {
      Set<SelectionKey> set = selector.selectedKeys();
      Iterator<SelectionKey> it = set.iterator();
      while (it.hasNext()) {
        SelectionKey key = it.next();
        it.remove();
        if (key.isAcceptable()) {
          ServerSocketChannel serverChannel = (ServerSocketChannel)key.channel();
          SocketChannel clientChannel = serverChannel.accept();
          clientChannel.configureBlocking(false);
          logger.info("远程连接{}:{}", clientChannel.socket().getInetAddress().getHostName(), clientChannel.socket().getPort());
          ByteBuffer buf = ByteBuffer.allocate(1024);
          clientChannel.register(selector, SelectionKey.OP_READ , buf);
        } else if (key.isWritable()) {
          SocketChannel clientChannel = (SocketChannel)key.channel();
          ByteBuffer buf = (ByteBuffer)key.attachment();
          buf.flip();
          clientChannel.write(buf);
          buf.compact();
        } else if (key.isReadable()) {
          SocketChannel clientChannel = (SocketChannel)key.channel();
          ByteBuffer buf = (ByteBuffer)key.attachment();
          clientChannel.read(buf);
          buf.flip();
          String s = decode(buf);
          logger.info("收到消息:{}", s);
          buf.clear();
        }
      }
    }
  }

  public ByteBuffer encode(String s) {
    return charset.encode(s);
  }

  public String decode(ByteBuffer buf) {
    CharBuffer charBuffer = charset.decode(buf);
    return charBuffer.toString();
  }

  public static void main(String[] args) throws IOException {
    Server server = new Server();
    server.service();
  }
}

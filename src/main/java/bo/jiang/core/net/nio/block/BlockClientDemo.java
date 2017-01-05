package bo.jiang.core.net.nio.block;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

/**
 * @author shumpert.jiang
 * @date 2017/1/4 0004 16:47
 */
public class BlockClientDemo {
  private static Logger logger = LogManager.getLogger(BlockClientDemo.class);
  private static final int PORT = 8000;
  private SocketChannel socketChannel;
  private boolean isConnected;

  public BlockClientDemo() throws IOException {
    socketChannel = SocketChannel.open();
    InetAddress inetAddress = InetAddress.getLocalHost();
    logger.info("发起连接到{}:{}", inetAddress.getHostName(), PORT);
    isConnected = socketChannel.connect(new InetSocketAddress(inetAddress, PORT));
    if (!isConnected) {
      logger.info("连接不成功");
    }
  }

  public BufferedReader getBufferedReader() throws IOException {
    InputStreamReader isr = new InputStreamReader(socketChannel.socket().getInputStream());
    return new BufferedReader(isr);
  }

  public PrintWriter getPrintWriter() throws IOException {
    OutputStreamWriter osw = new OutputStreamWriter(socketChannel.socket().getOutputStream());
    return new PrintWriter(osw);
  }

  public void talk() {
    if (!isConnected) return;
    logger.info("开始会话");
    BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
    BufferedReader br = null;
    PrintWriter pw = null;
    try {
      br = getBufferedReader();
      pw = getPrintWriter();
      String str = null;
      while ((str = consoleReader.readLine()) != null) {
        pw.println(str);
        pw.flush();
        logger.info("发送消息:{}", str);
        if ("bye".equals(str)) {
          break;
        }
        String s = br.readLine();
        logger.info("收到消息:{}", s);
      }
    } catch (IOException e) {
      logger.error("异常", e);
    } finally {
      try {
        socketChannel.close();
      } catch (IOException e) {
        logger.error("关闭流异常", e);
      }
    }

  }

  public static void main(String[] args) throws IOException {
    new BlockClientDemo().talk();
  }
}

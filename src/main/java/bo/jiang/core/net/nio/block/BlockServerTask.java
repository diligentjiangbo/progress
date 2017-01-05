package bo.jiang.core.net.nio.block;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.channels.SocketChannel;

/**
 * @author shumpert.jiang
 * @date 2017/1/4 0004 17:44
 */
public class BlockServerTask implements Runnable {
  private static Logger logger = LogManager.getLogger(BlockServerDemo.class);
  private SocketChannel socketChannel;

  public BlockServerTask(SocketChannel socketChannel) {
    this.socketChannel = socketChannel;
  }

  public BufferedReader getBufferedReader() throws IOException {
    InputStreamReader isr = new InputStreamReader(socketChannel.socket().getInputStream());
    return new BufferedReader(isr);
  }

  public PrintWriter getPrintWriter() throws IOException {
    OutputStreamWriter osw = new OutputStreamWriter(socketChannel.socket().getOutputStream());
    return new PrintWriter(osw);
  }

  @Override
  public void run() {
    logger.info("开始接收消息");
    BufferedReader br = null;
    PrintWriter pw = null;
    try {
      br = getBufferedReader();
      pw = getPrintWriter();
      String str = null;
      while ((str = br.readLine()) != null) {
        logger.info("收到消息:{}", str);
        if ("bye".equals(str)) {
          logger.info("断开连接");
          break;
        }
        String reStr = str.toUpperCase();
        pw.println(reStr);
        pw.flush();
        logger.info("返回消息:{}", str);
      }
    } catch (IOException e) {
      logger.error("异常", e);
    } finally {
      try {socketChannel.close();} catch (IOException e) {logger.error("关闭流异常", e);}
    }
  }
}

package bo.jiang.core.net.tcp.echo2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.Socket;

/**
 * @author shumpert.jiang
 */
public class Task implements Runnable {
  private static Logger logger = LogManager.getLogger(Task.class);
  private Socket socket;

  public Task(Socket socket) {
    this.socket = socket;
  }

  public BufferedReader getInputStream() throws IOException {
    InputStream inputStream =  socket.getInputStream();
    return new BufferedReader(new InputStreamReader(inputStream));
  }

  public PrintWriter getOutputStream() throws IOException {
    OutputStream outputStream =  socket.getOutputStream();
    return new PrintWriter(new OutputStreamWriter(outputStream));
  }

  public void run() {
    logger.info("来自{}:{}开始任务", socket.getInetAddress(), socket.getPort());
    PrintWriter pw = null;
    BufferedReader br = null;
    try {
      pw = getOutputStream();
      br = getInputStream();

        String str = null;
        while ((str = br.readLine()) != null) {
          logger.info("收到消息:{}", str);
          if ("bye".equals(str)) {
            logger.info("断开连接");
            break ;
          }
          String str2 = str.toUpperCase();
          pw.println(str2);
          pw.flush();
          logger.info("返回消息:{}", str2);
        }

    } catch (IOException e) {
      logger.error("异常", e);
    } finally {
      pw.close();
      try {
        br.close();
      } catch (IOException e) {
        logger.error("关闭流错误", e);
      }
    }
  }
}

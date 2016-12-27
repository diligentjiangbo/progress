package bo.jiang.core.net.tcp.echo2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.Socket;

/**
 * @author shumpert.jiang
 */
public class Client {
  private static Logger logger = LogManager.getLogger(Client.class);
  private Socket socket;

  public Client(String host, int port) throws IOException {
    socket = new Socket(host, port);
  }

  public BufferedReader getInputStream() throws IOException {
    InputStream inputStream =  socket.getInputStream();
    return new BufferedReader(new InputStreamReader(inputStream));
  }

  public PrintWriter getOutputStream() throws IOException {
    OutputStream outputStream =  socket.getOutputStream();
    return new PrintWriter(new OutputStreamWriter(outputStream));
  }


  public void start() {
    PrintWriter pw = null;
    BufferedReader br = null;
    BufferedReader brConsole = new BufferedReader(new InputStreamReader(System.in));
    try {
      pw = getOutputStream();
      br = getInputStream();
      String str = null;
      while ((str = brConsole.readLine()) != null) {
        pw.println(str);
        pw.flush();
        logger.info("发送消息{}", str);
        if ("bye".equals(str)) {
          logger.info("关闭客户端");
          break;
        }
        String str2 = br.readLine();
        logger.info("收到消息:{}", str2);
      }
    } catch (IOException e) {
      logger.error("异常", e);
    } finally {
      pw.close();
      try {
        br.close();
        brConsole.close();
      } catch (IOException e) {
        logger.error("关闭流错误", e);
      }
    }
  }

  public static void main(String[] args) {
    Client client = null;
    try {
       client = new Client("localhost", 8888);
    } catch (IOException e) {
      logger.error("启动客户端失败", e);
    }
    client.start();
  }
}

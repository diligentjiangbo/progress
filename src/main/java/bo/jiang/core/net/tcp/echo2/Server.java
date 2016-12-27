package bo.jiang.core.net.tcp.echo2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author shumpert.jiang
 */
public class Server {
  private static Logger logger = LogManager.getLogger(Server.class);
  private final ExecutorService executorService = Executors.newCachedThreadPool();
  private ServerSocket serverSocket;

  public Server(int port) throws IOException {
    serverSocket = new ServerSocket(port);
  }

  public void start() throws Exception {
    if (serverSocket == null) {
      logger.error("没有正确初始化服务器");
      throw new Exception("没有正确初始化服务器");
    }
    while (true) {
      Socket socket = serverSocket.accept();
      logger.info("{}:{}与服务器建立连接", socket.getInetAddress(), socket.getPort());
      executorService.execute(new Task(socket));
    }
  }

  public static void main(String[] args) {
    try {
      Server server = new Server(8888);
      server.start();
    } catch (Exception e) {
      logger.error("启动异常", e);
    }
  }
}

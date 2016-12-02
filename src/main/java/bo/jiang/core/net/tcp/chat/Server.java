package bo.jiang.core.net.tcp.chat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/17 0017.
 */
public class Server {
  private static Logger logger = LogManager.getLogger(Server.class);
  public static void main(String[] args) throws IOException {
    Map<String, Socket> socketMap = new HashMap<String, Socket>();
    ServerSocket serverSocket = new ServerSocket(10002);
    while (true) {
      Socket socket = serverSocket.accept();
      String ip = socket.getInetAddress().getHostAddress();
      int port = socket.getPort();
      logger.info("ip:{}, port:{}连接到客户端", ip, port);
      socketMap.put(ip + port, socket);
      new ServerTask(socketMap, socket).start();
    }
  }
}

package bo.jiang.core.net.tcp.chat;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2016/11/17 0017.
 */
public class ServerTask extends Thread{
  private Map<String, Socket> socketMap;
  private Socket socket;
  private static Logger logger = LogManager.getLogger(ServerTask.class);
  public ServerTask(Map<String, Socket> socketMap, Socket socket) {
    this.socketMap = socketMap;
    this.socket = socket;
  }
  @Override
  public void run() {
    try {
      InputStream inputStream = socket.getInputStream();
      int len;
      byte[] buffer = new byte[4096];
      String ip = socket.getInetAddress().getHostName();
      int port = socket.getPort();
      while ((len = inputStream.read(buffer)) != -1) {
        String str = new String(buffer, 0, len);
        logger.info("{}:{}", socket.getInetAddress().getHostAddress(), str);
        if ("exit".equals(str)) {
          socketMap.remove(ip + port);
          socket.close();
          logger.info("ip:{}, port:{} 断开连接", ip, port);
          break;
        }
        Set<Map.Entry<String, Socket>> set = socketMap.entrySet();
        for (Map.Entry<String, Socket> entry : set) {
          OutputStream os = entry.getValue().getOutputStream();
          os.write((ip + ":" + port + " said " + str).getBytes());
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

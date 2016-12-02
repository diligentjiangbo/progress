package bo.jiang.core.net.tcp.simple;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Administrator on 2016/11/16 0016.
 */
public class Server {
  public static void main(String[] args) throws IOException {
    ServerSocket serverSocket = new ServerSocket(9999);
    Socket socket = serverSocket.accept();
    OutputStream os = socket.getOutputStream();
    InputStream is = socket.getInputStream();
    int len;
    byte[] bytes = new byte[2048];
    while ((len = is.read(bytes)) != -1) {
      String s = new String(bytes, 0, len);
      System.out.println("收到消息：" + s);
      os.write(s.toUpperCase().getBytes());
    }
    socket.close();
    serverSocket.close();
  }
}

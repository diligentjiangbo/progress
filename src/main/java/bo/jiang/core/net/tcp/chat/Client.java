package bo.jiang.core.net.tcp.chat;

import java.io.*;
import java.net.Socket;

/**
 * Created by Administrator on 2016/11/17 0017.
 */
public class Client {

  public static void main(String[] args) throws IOException {
    Socket socket = new Socket("localhost", 10002);
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    OutputStream outputStream = socket.getOutputStream();
    InputStream inputStream = socket.getInputStream();
    new ClientTask(inputStream).start();
    System.out.println("请输入：");
    while (true) {
      String s = reader.readLine();
      outputStream.write(s.getBytes());
      if ("exit".equals(s)) {
        socket.close();
      }
    }
  }
}

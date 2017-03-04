package bo.jiang.core.net.tcp.echo;

import java.io.*;
import java.net.Socket;

/**
 * Created by Administrator on 2016/11/16 0016.
 */
public class Client {
  public static void main(String[] args) throws IOException {
    Socket socket = new Socket("192.168.228.131", 10001);
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    OutputStream outputStream = socket.getOutputStream();
    InputStream inputStream = socket.getInputStream();
    while (true) {
      System.out.print("请输入：");
      String s = reader.readLine();
      if ("exit".equals(s)) {
        break;
      }
      outputStream.write(s.getBytes());
      byte[] bytes = new byte[1024];
      int len;
      len = inputStream.read(bytes);
      System.out.println("返回的字符串为：" + new String(bytes, 0, len));
    }

    reader.close();
    socket.close();
  }
}

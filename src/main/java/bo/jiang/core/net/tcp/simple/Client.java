package bo.jiang.core.net.tcp.simple;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by Administrator on 2016/11/16 0016.
 */
public class Client {
  public static void main(String[] args) throws IOException {
    Socket socket = new Socket("192.168.228.131", 9999);
    OutputStream os = socket.getOutputStream();
    InputStream is = socket.getInputStream();
    os.write("Hello World".getBytes());
    byte[] bytes = new byte[1024];
    int len;
    while ((len = is.read(bytes)) != -1) {
      System.out.println("收到客户端回来的消息：" + new String(bytes, 0, len));
    }

    socket.close();
  }
}

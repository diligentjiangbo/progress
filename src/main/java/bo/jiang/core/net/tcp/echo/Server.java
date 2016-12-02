package bo.jiang.core.net.tcp.echo;

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
    ServerSocket serverSocket = new ServerSocket(10001);
    while (true) {
      new MyTask(serverSocket.accept()).start();
    }
  }
}

class MyTask extends Thread {
  Socket socket;
  public MyTask(Socket socket) {
    this.socket = socket;
  }

  public void run() {
    try {
      OutputStream outputStream = socket.getOutputStream();
      InputStream inputStream = socket.getInputStream();
      int len;
      byte[] bytes = new byte[4096];
      while ((len = inputStream.read(bytes)) != -1) {
        outputStream.write(new String(bytes, 0, len).getBytes());
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

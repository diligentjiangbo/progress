package bo.jiang.core.net.tcp.chat;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2016/11/17 0017.
 */
public class ClientTask extends Thread {
  private InputStream inputStream;

  public ClientTask(InputStream inputStream) {
    this.inputStream = inputStream;
  }
  @Override
  public void run() {
    int len;
    byte[] buffer = new byte[4096];
    try {
      while ((len = inputStream.read(buffer)) != -1) {
        System.out.println(new String(buffer, 0, len));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

package bo.jiang.core.net.nio.simple;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

/**
 * @author shumpert.jiang
 * @date 2017/1/6 0006 11:39
 */
public class Client {
  private static Logger logger  = LogManager.getLogger(Client.class);
  private static final int PORT = 8000;
  private SocketChannel socketChannel;

  public Client() throws IOException {
    socketChannel = SocketChannel.open();
    socketChannel.connect(new InetSocketAddress(InetAddress.getLocalHost(), PORT));
  }

  public void start() throws IOException {
    BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
    BufferedReader br = new BufferedReader(new InputStreamReader(socketChannel.socket().getInputStream()));
    PrintWriter pw = new PrintWriter(new OutputStreamWriter(socketChannel.socket().getOutputStream()));
    String str = null;
    while ((str = consoleReader.readLine()) != null) {
      logger.info("发送消息{}", str);
      pw.write(str);
      pw.flush();
    }
  }

  public static void main(String[] args) throws IOException {
    Client client = new Client();
    client.start();
  }
}

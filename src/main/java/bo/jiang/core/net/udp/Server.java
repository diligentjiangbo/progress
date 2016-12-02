package bo.jiang.core.net.udp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * Created by Administrator on 2016/11/17 0017.
 */
public class Server {
  private static Logger logger = LogManager.getLogger(Server.class);
  public static void main(String[] args) throws IOException {
    DatagramSocket datagramSocket = new DatagramSocket(20002);
    byte[] buffer = new byte[4096];
    DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
    logger.info("等待UDP数据包过来");
    datagramSocket.receive(datagramPacket);
    String ip = datagramPacket.getAddress().getHostAddress();
    int port = datagramPacket.getPort();
    byte[] data = datagramPacket.getData();
    String s = new String(data);
    logger.info("接收到来自{}:{}的UDP数据:{}", ip, port, s);

    byte[] sendByte = s.toUpperCase().getBytes();
    DatagramPacket sendPacket = new DatagramPacket(sendByte, sendByte.length, datagramPacket.getAddress(), port);
    datagramSocket.send(sendPacket);
  }
}

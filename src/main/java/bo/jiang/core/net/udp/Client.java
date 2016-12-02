package bo.jiang.core.net.udp;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by Administrator on 2016/11/17 0017.
 */
public class Client {
  private static Logger logger = LogManager.getLogger(Client.class);
  public static void main(String[] args) throws IOException {
    byte[] send = "Hello World".getBytes();
    InetAddress inetAddress = InetAddress.getByName("localhost");
    DatagramPacket datagramPacket = new DatagramPacket(send, send.length, inetAddress, 20002);
    DatagramSocket datagramSocket = new DatagramSocket();
    logger.info("发送UDP数据包");
    datagramSocket.send(datagramPacket);

    byte[] buffer = new byte[4096];
    DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
    datagramSocket.receive(receivePacket);
    String receiveStr = new String(receivePacket.getData());
    logger.info("收到来自{}:{}的回包:{}", receivePacket.getAddress().getHostAddress(), receivePacket.getPort(), receiveStr);
  }
}

package bo.jiang.core.net.tcp.ssl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;

/**
 * -Djavax.net.ssl.keyStore=C:\Users\Administrator\sslclientkeys
 * -Djavax.net.ssl.keyStorePassword=690302
 * -Djavax.net.ssl.trustStore=C:\Users\Administrator\sslclienttrust
 * -Djavax.net.ssl.trustStorePassword=690302
 */
public class Client {

  private static Logger logger = LogManager.getLogger(Client.class);

  public static void main(String[] args) throws IOException {
    logger.info("创建连接");
    //获取客户端套接字
    SSLSocket sslSocket = (SSLSocket) SSLSocketFactory.getDefault().createSocket("192.168.228.131", 7777);

    //获取输出流
    PrintWriter pw = new PrintWriter(new OutputStreamWriter(sslSocket.getOutputStream()));
    pw.println("Hello World");
    pw.flush();

    //获取输入流
    BufferedReader br = new BufferedReader(new InputStreamReader(sslSocket.getInputStream()));
    String s = br.readLine();
    System.out.println(s);

    br.close();
    pw.close();
    sslSocket.close();
    logger.info("关闭连接");
  }
}

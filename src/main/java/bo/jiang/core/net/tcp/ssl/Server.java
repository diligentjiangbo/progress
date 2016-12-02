package bo.jiang.core.net.tcp.ssl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import java.io.*;

/**
 * -Djavax.net.ssl.keyStore=C:\Users\Administrator\sslserverkeys
 * -Djavax.net.ssl.keyStorePassword=690302
 * -Djavax.net.ssl.trustStore=C:\Users\Administrator\sslservertrust
 * -Djavax.net.ssl.trustStorePassword=690302
 */
public class Server {

  private static Logger logger = LogManager.getLogger(Server.class);

  public static void main(String[] args) throws IOException {
    //获取SSL服务端套接字
    SSLServerSocket sslServerSocket = (SSLServerSocket) SSLServerSocketFactory.getDefault().createServerSocket(7777);

    logger.info("启动服务器，等待连接");
    SSLSocket sslSocket = (SSLSocket) sslServerSocket.accept();

    //获取套接字输入流
    BufferedReader br = new BufferedReader(new InputStreamReader(sslSocket.getInputStream()));
    System.out.println(br.readLine());

    //获取套接字输出流
    PrintWriter pw = new PrintWriter(new OutputStreamWriter(sslSocket.getOutputStream()));
    pw.write("收到消息，over");

    pw.close();
    br.close();
    sslSocket.close();
    sslServerSocket.close();
    logger.info("关闭服务器");
  }
}

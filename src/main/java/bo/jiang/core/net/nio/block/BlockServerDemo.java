package bo.jiang.core.net.nio.block;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author shumpert.jiang
 * @date 2017/1/4 0004 16:48
 */
public class BlockServerDemo {
  private static Logger logger = LogManager.getLogger(BlockServerDemo.class);
  private static final int PORT = 8000;
  private ExecutorService executorService;
  private ServerSocketChannel serverSocketChannel;

  public BlockServerDemo() throws IOException {
    executorService = Executors.newCachedThreadPool();
    serverSocketChannel = ServerSocketChannel.open();
    serverSocketChannel.bind(new InetSocketAddress(PORT));
  }

  public void service() throws IOException {
    while (true) {
      executorService.execute(new BlockServerTask(serverSocketChannel.accept()));
    }
  }

  public static void main(String[] args) throws IOException {
    new BlockServerDemo().service();
  }
}

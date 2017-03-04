package bo.jiang.extend.netty.channelpool;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.pool.ChannelPool;
import io.netty.channel.pool.FixedChannelPool;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 简单的客户端小例子。
 * 向服务器端写数据，读出服务器端返回的数据并打印到控制台。
 */
public class EchoClient {
  private static ChannelPool channelPool;
  private static ClientHandler clientHandler;

  public static void main(String[] args) {
//    EventLoopGroup group = new NioEventLoopGroup();
//    try {
//      Bootstrap b = new Bootstrap();
//      b.group(group)
//          .channel(NioSocketChannel.class)
//          .handler(new ChannelInitializer<SocketChannel>() {
//            protected void initChannel(SocketChannel socketChannel) throws Exception {
//              socketChannel.pipeline()
//                  .addLast(new ClientHandler());
//            }
//          });
//
//      ChannelFuture channelFuture = b.connect(new InetSocketAddress(6666)).sync();
//      channelFuture.channel().closeFuture().sync();
//    } catch (InterruptedException e) {
//      e.printStackTrace();
//    } finally {
//      group.shutdownGracefully();
//    }

    EventLoopGroup group = new NioEventLoopGroup();
    Bootstrap b = new Bootstrap();
    b.group(group).channel(NioSocketChannel.class);
    InetSocketAddress inetSocketAddress = new InetSocketAddress(6666);
    SimpleChannelPoolHandler simpleChannelPoolHandler = new SimpleChannelPoolHandler();
    channelPool = new FixedChannelPool(b.remoteAddress(inetSocketAddress), simpleChannelPoolHandler, 1);
    clientHandler = new ClientHandler(channelPool);
    simpleChannelPoolHandler.setChannelPool(clientHandler);
    while (true) {
      long startTime = System.currentTimeMillis();
      CountDownLatch countDownLatch = new CountDownLatch(1);
      for (int i = 0; i < 1; i++) {
        clientHandler.setCountDownLatch(countDownLatch);
        channelPool.acquire();
        //System.out.println(future == null);
        //future.addListener(new MyFutureListener());
      }
//      try {
//        Thread.sleep(2000);
//      } catch (InterruptedException e) {
//        e.printStackTrace();
//      }
      try {
        boolean isDone = countDownLatch.await(10000, TimeUnit.MILLISECONDS);
        if (!isDone) {
          System.out.println("收到回包超时");
        }
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

//      long endTime = System.currentTimeMillis();
//      long spend = endTime - startTime;
//      System.out.println("花费时间" + spend + "ms");
//      if (spend < 500) {
//        try {
//          Thread.sleep(2000 - spend);
//        } catch (InterruptedException e) {
//          e.printStackTrace();
//        }
//      }

    }
  }

}

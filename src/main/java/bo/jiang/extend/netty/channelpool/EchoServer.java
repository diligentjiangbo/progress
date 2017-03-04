package bo.jiang.extend.netty.channelpool;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * 简单的服务器端小例子。
 * 接收客户端的数据，并向客户端回复数据。
 */
public class EchoServer {

  public static void main(String[] args) {
    EventLoopGroup work = new NioEventLoopGroup();
    EventLoopGroup boss = new NioEventLoopGroup();
    try {
      ServerBootstrap b = new ServerBootstrap();
      b.group(work, boss)
          .channel(NioServerSocketChannel.class)
          .childHandler(new ChannelInitializer<SocketChannel>() {
            protected void initChannel(SocketChannel socketChannel) throws Exception {
              socketChannel.pipeline()
                  .addLast(new ChannelInboundHandlerAdapter() {

                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                      String str = MyByteBufUtil.byteBuf2String((ByteBuf)msg);
                      System.out.println(str);
                      ByteBuf retObj = MyByteBufUtil.string2ByteBuf(str.toUpperCase());
                      Thread.sleep(3000);
                      ctx.writeAndFlush(retObj);
                    }
                  });
            }
          });
      b.bind(new InetSocketAddress(6666)).sync().channel().closeFuture().sync();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      work.shutdownGracefully();
      boss.shutdownGracefully();
    }
  }

}

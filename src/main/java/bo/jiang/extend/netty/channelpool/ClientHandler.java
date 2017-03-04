package bo.jiang.extend.netty.channelpool;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.pool.ChannelPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.CountDownLatch;

/**
 * @author shumpert.jiang
 * @date 2017/3/2 0002 20:46
 */
@ChannelHandler.Sharable
public class ClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
  private static Logger logger = LogManager.getLogger(ClientHandler.class);
  private CountDownLatch countDownLatch = new CountDownLatch(0);
  private ChannelPool channelPool;

  public ClientHandler(ChannelPool channelPool) {
    this.channelPool = channelPool;
  }

  public void setCountDownLatch(CountDownLatch countDownLatch) {
    this.countDownLatch = countDownLatch;
  }

  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    logger.info("发送消息");
    sendRequest(ctx.channel());
  }

  protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
    String str = MyByteBufUtil.byteBuf2String(byteBuf);
    System.out.println(str);
    //System.out.println(channelPool == null);
    countDownLatch.countDown();
    channelPool.release(channelHandlerContext.channel());
  }

  public void sendRequest(Channel channel) {
    ByteBuf byteBuf = MyByteBufUtil.string2ByteBuf("Hello World");
    channel.writeAndFlush(byteBuf);
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    logger.error("异常", cause);
  }
}

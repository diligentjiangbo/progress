package bo.jiang.extend.netty.channelpool;

import io.netty.channel.Channel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;

/**
 * @author shumpert.jiang
 * @date 2017/3/3 0003 10:58
 */
public class MyFutureListener implements FutureListener<Channel> {
  public void operationComplete(Future<Channel> channelFuture) throws Exception {
    if (channelFuture.isSuccess()) {
      System.out.println("success");
      //Thread.sleep(1000);
      //channelPool.release(channelFuture.getNow());
    } else {
      System.out.println("fail");
    }
  }
}

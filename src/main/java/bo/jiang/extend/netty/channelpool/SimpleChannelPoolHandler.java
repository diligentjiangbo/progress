package bo.jiang.extend.netty.channelpool;

import io.netty.channel.Channel;
import io.netty.channel.pool.ChannelPoolHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author shumpert.jiang
 * @date 2017/3/2 0002 17:38
 */
public class SimpleChannelPoolHandler implements ChannelPoolHandler {
  private static final Logger logger = LogManager.getLogger(SimpleChannelPoolHandler.class);
  private ClientHandler clientHandler;

  public void setChannelPool(ClientHandler clientHandler) {
    this.clientHandler = clientHandler;
  }

  public void channelReleased(Channel ch) throws Exception {
    logger.debug("channel released: {}", ch.toString());
  }


  public void channelAcquired(Channel ch) throws Exception {
    logger.debug("channel acquired: {}", ch.toString());
    logger.info("发送消息");
    clientHandler.sendRequest(ch);
  }


  public void channelCreated(Channel ch) throws Exception {
    ch.pipeline()
        .addLast(new ReadTimeoutHandler(2)).addLast(clientHandler);
    logger.debug("channel created: {}", ch.toString());
  }
}

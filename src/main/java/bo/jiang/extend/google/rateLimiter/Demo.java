package bo.jiang.extend.google.rateLimiter;

import com.google.common.util.concurrent.RateLimiter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.TimeUnit;

/**
 * 1.指定的桶大小指每秒放入桶多少个令牌，桶满了就会丢弃令牌。
 * 2.acquire(1)和acquire（10）在请求时没区别，即使没有十个令牌也可以获得，只是会用后面的令牌来进行补偿。
 */
public class Demo {

  private static Logger logger = LogManager.getLogger(Demo.class);

  public static void main(String[] args) throws InterruptedException {
    method2();
  }

  /**
   * 本来一秒只能放入指定的10个令牌，通过睡眠没有使用令牌，
   * 桶里就一直容纳10个令牌，没有多余令牌能进入。
   * 所以我们可以在拿了10个令牌后无需等待再继续拿。
   * @throws InterruptedException
   */
  private static void method1() throws InterruptedException {
    //每秒钟放入10个令牌
    RateLimiter rateLimiter = RateLimiter.create(10.0);
    logger.info("睡眠2秒");
    TimeUnit.MILLISECONDS.sleep(2000);
    rateLimiter.acquire(10);
    logger.info("获取10个令牌");
    rateLimiter.acquire();
    logger.info("再获取一个令牌，无需等待");
  }

  /**
   * 一次性拿取多个令牌，在下次拿的时候进行补偿。
   */
  private static void method2() {
    RateLimiter rateLimiter = RateLimiter.create(10.0);
    rateLimiter.acquire(20);
    logger.info("可以直接拿去多个令牌");
    rateLimiter.acquire();
    logger.info("不过下次再拿取就会等待一定时间进行补偿");
  }
}

package bo.jiang.core.thread.threadgroup;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.TimeUnit;

/**
 * @author shumpert.jiang
 */
public class Demo implements Runnable{
  private static Logger logger = LogManager.getLogger(Demo.class);

  public void run() {
    while (true) {
      logger.info("{}正在运行",Thread.currentThread().getThreadGroup().getName());
      try {
        TimeUnit.MILLISECONDS.sleep(1000);
      } catch (InterruptedException e) {
        logger.error("异常", e);
      }
    }
  }

  public static void main(String[] args) {
    ThreadGroup threadGroup = new ThreadGroup("myGroup");
    for (int i = 0; i < 5; i++) {
      Thread thread = new Thread(threadGroup, new Demo());
      thread.start();
    }
    threadGroup.list();

    try {
      TimeUnit.MILLISECONDS.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    Thread[] threads = new Thread[threadGroup.activeCount()];
    threadGroup.enumerate(threads);
    for (int i = 0; i < threads.length; i++) {
      logger.info(threads[i].getState());
    }
  }
}

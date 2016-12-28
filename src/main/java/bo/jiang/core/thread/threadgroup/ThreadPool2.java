package bo.jiang.core.thread.threadgroup;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author shumpert.jiang
 * 使用BlockingQueue实现
 * 比ThreadPool的缺点是未涉及join方法，因为如果用BlockingQueue的take方法实现，
 * 那么如果没有任务进入队列，线程将一直阻塞，不能唤醒线程去判断isClosed,从而返回null
 */
public class ThreadPool2 extends ThreadGroup {
  private static int poolId = 0;
  private int threadId = 0;
  private boolean isClosed = false;
  private BlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(100);

  public ThreadPool2(int size) {
    super("ThreadPool-" + poolId++);
    setDaemon(true);
    while (size-- > 0) {
      new Worker().start();
    }
  }

  public void execute(Runnable runnable) {
    if (isClosed) {
      throw new IllegalStateException();
    }
    if (runnable != null) {
      queue.add(runnable);
    }
  }

  public Runnable getTask() {
    //在线程拿到null时，判断isClosed，如果已关闭退出线程
    return queue.poll();
  }

  public void close() {
    isClosed = true;
  }

  private class Worker extends Thread {
    Worker() {
      super("thread-" + threadId++);
    }
    public void run() {
      while (!isClosed) {
        Runnable runnable = null;
        runnable = getTask();
        if (runnable != null) {
          runnable.run();
        }
      }
    }
  }
}

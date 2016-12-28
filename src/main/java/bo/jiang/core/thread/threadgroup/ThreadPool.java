package bo.jiang.core.thread.threadgroup;

import java.util.LinkedList;

/**
 * @author shumpert.jiang
 */
public class ThreadPool extends ThreadGroup {
  private static int threadPoolId = 0;
  private int threadId = 0;
  private boolean isClosed = false;
  private LinkedList<Runnable> list = new LinkedList<Runnable>();

  public ThreadPool(int size) {
    super("ThreadPool-" + threadPoolId++);
    setDaemon(true);
    while (size-- > 0) {
      new Worker().start();
    }
  }

  public synchronized void execute(Runnable runnable) {
    if (isClosed) {
      throw new IllegalStateException();
    }
    if (runnable != null) {
      list.add(runnable);
      notify();
    }
  }

  public synchronized Runnable getTask() throws InterruptedException {
    while (list.size() == 0) {
      if (isClosed) {
        //join的时候，如果还有没处理的任务就会处理，不然返回null，在线程中退出
        return null;
      }
      wait();
    }
    return list.removeFirst();
  }

  public synchronized void close() {
    if (!isClosed) {
      isClosed = true;
      list.clear();
      interrupt();
    }
  }

  public void join() {
    synchronized (this) {
      isClosed = true;
      notifyAll();
    }
    Thread[] threads = new Thread[activeCount()];
    enumerate(threads);
    for (int i = 0; i < threads.length; i++) {
      try {
        threads[i].join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  private class Worker extends Thread {
    Worker() {
      super(ThreadPool.this, "threadId-" + threadId++);
    }
    public void run() {
      //调用close方法关闭后，如果线程任务中没有阻塞，那么任务会执行完，然后循环判断isInterrupted退出
      while (!isInterrupted()) {
        Runnable runnable = null;
        try {
          runnable = getTask();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        if (runnable == null) {
          break;//join后返回null，在这退出循环
        }
        runnable.run();
      }
    }
  }
}

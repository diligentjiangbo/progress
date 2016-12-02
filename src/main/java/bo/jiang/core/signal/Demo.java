package bo.jiang.core.signal;

import sun.misc.Signal;
import sun.misc.SignalHandler;

import java.util.concurrent.TimeUnit;

/**
 * 此例子应运行在Linux下
 * 信号量回调函数，在程序运行时可以通过kill向其发送信号量调用回调函数。
 * kill -s SIGUSR2 [pid]
 * SIGUSER1 SIGUSER2是留给用户使用的两个信号量
 */
public class Demo implements SignalHandler{
  public void handle(Signal signal) {
    signalCallback(signal);
  }

  private void signalCallback(Signal sn) {
    System.out.println(sn.getName() + " is received.");
  }

  public static void main(String[] args) throws InterruptedException {
    Demo demo = new Demo();
    Signal sig = new Signal("USR2");
    Signal.handle(sig, demo);
    //给我们时间来发送信号量给程序
    TimeUnit.MILLISECONDS.sleep(15000);
  }
}

package bo.jiang.core.jvm.memory;

/**
 * VM Args: -Xss2M 栈内存设置大一点，可以建立的线程数量就少，建立线程就越容易把剩下的内存耗尽
 */
public class JavaVMStackOOM {
  private void dontStop() {
    while (true);
  }

  public void stackLeakByThread() {
    while (true) {
      new Thread(new Runnable() {
        public void run() {
          dontStop();
        }
      }).start();
    }
  }

  public static void main(String[] args) throws Throwable {
    JavaVMStackOOM oom = new JavaVMStackOOM();
    oom.stackLeakByThread();
  }
}

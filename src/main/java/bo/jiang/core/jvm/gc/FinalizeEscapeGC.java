package bo.jiang.core.jvm.gc;

import java.util.concurrent.TimeUnit;

/**
 * 1.对象可以在被GC时自救。
 * 2.这种自救的机会只有一次，因为一个对象的finalize（）方法最多只会被系统调用一次。
 */
public class FinalizeEscapeGC {
  public static FinalizeEscapeGC SAVE_HOOK = null;

  public void isAlive() {
    System.out.println("I am alive :)");
  }

  @Override
  protected void finalize() throws Throwable {
    super.finalize();
    System.out.println("finalize is execing");
    SAVE_HOOK = this;
  }

  public static void main(String[] args) {
    SAVE_HOOK = new FinalizeEscapeGC();
    SAVE_HOOK = null;
    System.gc();
    try {
      TimeUnit.MILLISECONDS.sleep(500);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    if (SAVE_HOOK != null) {
      SAVE_HOOK.isAlive();
    } else {
      System.out.println("i am dead :(");
    }

    SAVE_HOOK = null;
    System.gc();
    try {
      TimeUnit.MILLISECONDS.sleep(500);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    if (SAVE_HOOK != null) {
      SAVE_HOOK.isAlive();
    } else {
      System.out.println("i am dead :(");
    }
  }
}

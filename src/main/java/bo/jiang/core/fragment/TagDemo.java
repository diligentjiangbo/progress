package bo.jiang.core.fragment;

/**
 * tag标签的使用
 */
public class TagDemo {
  public static void main(String[] args) {
    breakTagDemo();
  }

  /**
   * 在一个循环前面打一个tag（"HERE"），就可以通过这个tag来在内循环中break外循环
   */
  public static void breakTagDemo() {
    HERE:while (true) {
      int i = 0;
      while (true) {
        System.out.println("Hello World.");
        if (i++ == 10) {
          break HERE;
        }
      }
    }
  }

}

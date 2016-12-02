package bo.jiang.core.enumeration;

/**
 * 枚举类使用例子
 */
public enum Demo {

  HELLO("hello", 1),
  WORLD("World", 2);

  private String name;
  private int age;

  private Demo(String name, int age) {
    this.name = name;
    this.age = age;
  }

  public String getName() {
    return name;
  }

  public int getAge() {
    return age;
  }

  public static String getName(int index) {
    for (Demo demo : Demo.values()) {
      if (demo.getAge() == index) {
        return demo.getName();
      }
    }
    return null;
  }

  public static void main(String[] args) {
    System.out.println(Demo.HELLO.getName());
    System.out.println(Demo.getName(2));
  }


}

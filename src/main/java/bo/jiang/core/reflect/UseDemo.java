package bo.jiang.core.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 反射的一些基本用法
 */
public class UseDemo {
  public static void main(String[] args) throws Exception {
    getField2();
  }

  public String name;
  private int i;

  public UseDemo() {}

  public UseDemo(int i) {
    this.i = i;
  }

  public void method1() {
    System.out.println("public method");
  }

  private void method2() {
    System.out.println("private method");
  }

  public String toString() {
    return "UseDemo i=" + i;
  }

  /**
   * 反射获取私有字段
   * @throws Exception
   */
  public static void getField2() throws Exception {
    UseDemo useDemo = new UseDemo();
    Field field = UseDemo.class.getDeclaredField("i");
    field.setAccessible(true);
    field.setInt(useDemo, 7);
    int i = field.getInt(useDemo);
    System.out.println(i);
  }

  /**
   * 反射获取公有字段
   * @throws Exception
   */
  public static void getField1() throws Exception {
    UseDemo useDemo = new UseDemo();
    Field field = UseDemo.class.getField("name");
    field.set(useDemo, "Hello World");
    String str = (String) field.get(useDemo);
    System.out.println(str);
  }

  /**
   * 反射调用私有方法
   * @throws Exception
   */
  public static void invokeMethod2() throws Exception {
    Method method = UseDemo.class.getDeclaredMethod("method2");
    method.setAccessible(true);//设置后才能访问私有方法
    method.invoke(new UseDemo());
  }

  /**
   * 反射调用公有方法
   * @throws Exception
   */
  public static void invokeMethod1() throws Exception {
    Method method = UseDemo.class.getMethod("method1");
    method.invoke(new UseDemo());
  }

  /**
   * 通过有参构造函数创建对象
   * @throws Exception
   */
  public static void getInstance2() throws Exception {
    Class<?> clazz = Class.forName("bo.jiang.core.reflect.UseDemo");
    Class[] parameters = new Class[]{int.class};
    Constructor con = clazz.getConstructor(parameters);
    UseDemo useDemo = (UseDemo)con.newInstance(7);
    System.out.println(useDemo);
  }

  /**
   * 通过无参构造函数创建对象
   * @throws Exception
   */
  public static void getInstance1() throws Exception {
      Class<?> clazz = Class.forName("bo.jiang.core.reflect.UseDemo");
      UseDemo useDemo = (UseDemo)clazz.newInstance();
      System.out.println(useDemo);
  }
}

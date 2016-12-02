package bo.jiang.extend.google.gson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.google.gson.Gson;

/**
 * Created by Administrator on 2016/9/19 0019.
 */
public class Demo {
  private static Gson gson = new Gson();
  public static void main(String[] args) {
    containObjDemo();
  }

  private static void parseEnumDemo() {
    C c = new C(Color.RED, "red");
    String cStr = gson.toJson(c);
    System.out.println("转换来的字符串:" + cStr);
    C cObj = gson.fromJson(cStr, C.class);
    System.out.println(cObj);
  }

  /**
   * fastjson不能转换对象中包含对象的情况，但gson能。
   */
  private static void containObjDemo() {
    B b = new B("B");
    A a = new A("A", 1, b);
    System.out.println("fastjson-=-=-=--====-=-=-==-=-=");
    String as = JSON.toJSONString(a);
    System.out.println("json字符串:" + as);
    A ac = null;
    try {
       ac =JSON.parseObject(as, A.class);
    } catch (JSONException e) {
      System.out.println("fastjson在转换回来的时候会抛出异常");
    }
    System.out.println(ac);
    System.out.println("fastjson-=-=-=--====-=-=-==-=-=");

    System.out.println("gson-=-=-=--====-=-=-==-=-=");
    String gsonAs = gson.toJson(a);
    System.out.println("json字符串:" + gsonAs);
    A gsonAc = gson.fromJson(gsonAs, A.class);
    System.out.println("还原的A对象:" + gsonAc);
    System.out.println("gson-=-=-=--====-=-=-==-=-=");
  }
}

class A {
  String name;
  int age;
  B b;


  public A(String name, int age, B b) {
    this.name = name;
    this.age = age;
    this.b = b;
  }

  public String getName() {
    return name;
  }

  public int getAge() {
    return age;
  }

  public B getB() {
    return b;
  }

  @Override
  public String toString() {
    return "A{" +
        "name='" + name + '\'' +
        ", age=" + age +
        ", b=" + b +
        '}';
  }
}

class B {
  String name;
  public B(String name) {
    this.name = name;
  }

  public B(){}

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "B{" +
        "name='" + name + '\'' +
        '}';
  }
}

class C {
  Color color;
  String name;
  public C (Color color, String name) {
    this.color = color;
    this.name = name;
  }

  public String toString() {
    return "color: " + color + ", name: " + name;
  }
}

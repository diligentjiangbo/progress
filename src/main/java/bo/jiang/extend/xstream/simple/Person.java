package bo.jiang.extend.xstream.simple;

/**
 * 要转换为xml的对象
 */
public class Person {
  private String name;
  private int age;
  private PhoneNumber phoneNumber;

  public Person(String name, int age, PhoneNumber phoneNumber) {
    this.name = name;
    this.age = age;
    this.phoneNumber = phoneNumber;
  }

  @Override
  public String toString() {
    return "Person{" +
        "name='" + name + '\'' +
        ", age=" + age +
        ", phoneNumber=" + phoneNumber +
        '}';
  }
}

package bo.jiang.extend.google.protobuf;

import java.io.IOException;

/**
 * protobuf是将对象序列化的一种技术，性能很高。
 * protobuf根据自定义的.proto文件生成指定java类。
 * 学习时花较多时间的地方是安装protobuf complier。
 */
public class Demo {
  public static void main(String[] args) throws IOException {
    //生成一个需要序列化的对象
    PersonSerial.Person.Builder person = PersonSerial.Person.newBuilder();
    person.setName("Hello");
    person.setAge(1);

    //将对象序列化为流
//    FileOutputStream fos = new FileOutputStream("c:\\protobuf.txt");
//    person.build().writeTo(fos);
//    FileInputStream fis = new FileInputStream("c:\\protobuf.txt");
//    PersonSerial.Person person1 = PersonSerial.Person.parseFrom(fis);

    //将对象序列化为字节
    byte[] bytes = person.build().toByteArray();
    System.out.println(bytes);
    PersonSerial.Person person2 = PersonSerial.Person.parseFrom(bytes);
    System.out.println(person2.getName() + ":" + person2.getAge());

  }
}

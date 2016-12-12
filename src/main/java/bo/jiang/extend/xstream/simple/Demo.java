package bo.jiang.extend.xstream.simple;

import com.thoughtworks.xstream.XStream;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * simple xstream demo
 */
public class Demo {
  public static void main(String[] args) throws FileNotFoundException {
    XStream xStream = new XStream();

    //设置别名，不然在xml中会使用类的全路径名
    xStream.alias("person", Person.class);
    xStream.alias("phoneNumber", PhoneNumber.class);

    //构造要转换成xml的对象
    PhoneNumber phoneNumber = new PhoneNumber(021, 12345678);
    Person person = new Person("shumpert", 23, phoneNumber);

    //直接转换为xml字符串
    String str = xStream.toXML(person);
    System.out.println(str);

    //将xml字符串转换回来
    Person person1 = (Person)xStream.fromXML(str);
    System.out.println(person1);

    //将对象转换的xml写到输出流
    OutputStream outputStream = new FileOutputStream("c:\\my.xml");
    xStream.toXML(person, outputStream);
  }
}

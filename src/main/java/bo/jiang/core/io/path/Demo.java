package bo.jiang.core.io.path;

import java.io.File;

/**
 * class.getResource()获取的是类文件的同级目录
 * 如bo.jiang.Demo这个java类生成的类文件会存放在一个bo/jiang/文件夹下
 * 那么如果file在bo/jiang/文件夹下就可以这样获取：Demo.class.getResource("file")
 * 如果file在class根目录也就是和bo/jiang/在同级目录，那么就是:Demo.class.getResource("/file")
 *
 * class.getClassLoader().getResource()获取的是类文件的根目录
 * 上面的例子Demo.class.getClassLoader().getResource("file") 等同于 Demo.class.getResource("/file")
 *
 * getResource()和getResourceAsStream()找的路径一样只是返回的值不同，一个是URL，一个是输入流
 *
 */
public class Demo {
  public static void main(String[] args) {
    File file = new File(Demo.class.getResource("/log4j2.xml").getFile());
    File file2 = new File(Demo.class.getClassLoader().getResource("log4j2.xml").getFile());
    System.out.println("Demo.class.getResource(\"/log4j2.xml\") : " + file.getAbsolutePath());
    System.out.println("Demo.class.getClassLoader().getResource(\"log4j2.xml\"): " + file2.getAbsolutePath());

    System.out.println(System.getProperty("user.dir"));
  }
}

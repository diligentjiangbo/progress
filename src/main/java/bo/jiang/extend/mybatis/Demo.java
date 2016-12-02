package bo.jiang.extend.mybatis;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;

/**
 * mybatis使用的简单例子
 */
public class Demo {
  public static void main(String[] args) {
    InputStream inputStream = Demo.class.getClassLoader().getResourceAsStream("mybatis.xml");
    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    SqlSession session = sqlSessionFactory.openSession();
    try {
      Person person = session.selectOne("bo.jiang.mybatis.mybatisMapper.selectPerson", 0);
      System.out.println(person);
    } finally {
      session.close();
    }
  }
}

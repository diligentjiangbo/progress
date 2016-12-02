package bo.jiang.core.annotation;

import java.lang.reflect.Field;

/**
 * Created by Administrator on 2016/11/1 0001.
 */
public class FruitTest {
  @FruitName("apple")
  String name;

  @FruitColor(FruitColor.Color.YELLOW)
  String color;

  /**
   * 根据反射拿到注解的值
   * @param args
   * @throws NoSuchFieldException
   */
  public static void main(String[] args) throws NoSuchFieldException {
    Field field = FruitTest.class.getDeclaredField("name");
    FruitName fruitName = field.getAnnotation(FruitName.class);
    System.out.println(fruitName.value());
  }
}

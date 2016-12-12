package bo.jiang.extend.xstream.simple;

/**
 * 要转换为xml的对象
 */
public class PhoneNumber {
  private int code;
  private int number;

  public PhoneNumber(int code, int number) {
    this.code = code;
    this.number = number;
  }

  @Override
  public String toString() {
    return "PhoneNumber{" +
        "code=" + code +
        ", number=" + number +
        '}';
  }
}

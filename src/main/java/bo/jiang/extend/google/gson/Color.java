package bo.jiang.extend.google.gson;

/**
 * Created by Administrator on 2016/10/20 0020.
 */
public enum Color {
  RED(0),YELLOW(1),GREEN(2);
  private int index;
  private Color(int index) {
    this.index = index;
  }

  public int getIndex() {
    return index;
  }

  public static void main(String[] args) {
    Color color = Color.GREEN;
    System.out.println(color.ordinal());
    System.out.println(color.getIndex());
  }
}

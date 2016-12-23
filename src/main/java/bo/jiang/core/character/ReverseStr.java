package bo.jiang.core.character;

/**
 * @author shumpert.jiang
 * 逆转字符串
 */
public class ReverseStr {
  public static void main(String[] args) {
    String str = "Hello World";
    String str2 = reverse(str);
    System.out.println(str2);
  }

  public static String reverse(String str) {
    char[] chs = str.toCharArray();
    int n = chs.length - 1;
    for (int j = (n-1) >> 1; j >= 0; j--) {
      int i = n - j;
      char temp = chs[i];
      chs[i] = chs[j];
      chs[j] = temp;
    }
    return new String(chs);
  }
}

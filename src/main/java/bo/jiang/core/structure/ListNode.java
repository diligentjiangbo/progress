package bo.jiang.core.structure;

import java.util.Random;

/**
 * @author shumpert.jiang
 */
public class ListNode {
  private int val;
  private ListNode next;
  
  public ListNode(int val) {
    this.val = val;
  }
  
  public int getVal() {
    return this.val;
  }
  
  public ListNode getNext() {
    return this.next;
  }

  /**
   * 反转链表
   * @param node
   * @return
   */
  public static ListNode reverseListNode(ListNode node) {
    if (node == null) {
      return null;
    }
    ListNode pre = null;
    ListNode next = null;
    while (node != null) {
      next = node.next;
      node.next = pre;
      pre = node;
      node = next;
    }
    return pre;
  }

  /**
   * 创建指定大小的链表
   * @param size
   * @return
   */
  public static ListNode createList(int size) {
    if (size <= 0) {
      return null;
    }
    Random random = new Random(System.currentTimeMillis());
    ListNode header = new ListNode(random.nextInt(100));
    ListNode node = header;
    for (int i = 1; i < size; i++) {
      node.next = new ListNode(random.nextInt(100));
      node = node.next;
    }
    return header;
  }

  /**
   * 打印链表的值
   * @param node
   */
  public static void show(ListNode node) {
    StringBuilder sb = new StringBuilder("{");
    boolean flag = true;
    while (node != null) {
      if (flag) {
        flag = false;
        sb.append(node.val);
      } else {
        sb.append(",");
        sb.append(node.val);
      }
      node = node.next;
    }
    sb.append("}");
    System.out.println(sb.toString());
  }
}

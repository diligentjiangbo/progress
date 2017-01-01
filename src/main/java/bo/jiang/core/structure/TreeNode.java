package bo.jiang.core.structure;

/**
 * @author shumpert.jiang
 */
public class TreeNode {
  private int val;
  private TreeNode left;
  private TreeNode right;

  public TreeNode(int val) {
    this.val = val;
  }

  public int getVal() {
    return this.val;
  }

  public TreeNode getLeft() {
    return this.left;
  }

  public TreeNode getRight() {
    return this.right;
  }

  /**
   * 根据指定数组创建一个二叉树
   * @param arr
   * @return
   */
  public static TreeNode createTree(int[] arr) {
    if (arr.length <= 0) {
      return null;
    }
    TreeNode header = new TreeNode(arr[0]);
    for (int i = 1; i < arr.length; i++) {
      addNode(header, arr[i]);
    }
    return header;
  }

  /**
   * 将一个新的节点插入二叉树
   * @param node
   * @param val
   */
  public static void addNode(TreeNode node, int val) {
    if (val <= node.val) {
      if (node.left == null) {
        node.left = new TreeNode(val);
      } else {
        addNode(node.left, val);
      }
    } else {
      if (node.right == null) {
        node.right = new TreeNode(val);
      } else {
        addNode(node.right, val);
      }
    }
  }

  /**
   * 前序遍历
   * @param node
   */
  public static void preShow(TreeNode node) {
    if (node == null) {
      return;
    }
    System.out.println(node.val);
    preShow(node.left);
    preShow(node.right);
  }

  /**
   * 中序遍历
   * @param node
   */
  public static void inShow(TreeNode node) {
    if (node == null) {
      return;
    }
    inShow(node.left);
    System.out.println(node.val);
    inShow(node.right);
  }

  /**
   * 后序遍历
   * @param node
   */
  public static void postShow(TreeNode node) {
    if (node == null) {
      return;
    }
    postShow(node.left);
    postShow(node.right);
    System.out.println(node.val);
  }
}

package bo.jiang.core.jvm.gc;

/**
 * 打印分析各种内存分配情况
 */
public class MemoryGCAnalyse {

  private static final int _1MB = 1024 * 1024;

  public static void main(String[] args) {
    //testAllocation();
    //testPretenureSizeThreshold();
    testTenuringThreshold();
  }

  /**
   * VM Args: -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8
   * 当新生代内存不足的时候，会将新生代还存活的对象拷贝到老年代去，然后再在新生代分配内存。
   */
  public static void testAllocation() {
    byte[] allocation1, allocation2, allocation3, allocation4;
    allocation1 = new byte[3 * _1MB];
    allocation2 = new byte[3 * _1MB];
    allocation3 = new byte[3 * _1MB];
    allocation4 = new byte[3 * _1MB];
  }

  /**
   * VM Args: -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8
   * -XX:PretenureSizeThreshold=3145728
   * 上面这个参数设置大于3M的对象将直接放入老年代，不能直接写3M
   * 新生代采用Parallel Scavenge GC时无效
   */
  public static void testPretenureSizeThreshold() {
    byte[] allocation;
    allocation = new byte[5 * _1MB];
  }

  /**
   * VM Args: -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8
   * -XX:MaxTenuringThreshold=1
   * -XX:+PrintTenuringDistribution
   * 未成功，原因不明，暂不深究。
   */
  public static void testTenuringThreshold() {
    byte[] allocation1, allocation2, allocation3;
    allocation1 = new byte[_1MB / 4];
    allocation2 = new byte[4 * _1MB];
    allocation3 = new byte[4 * _1MB];
    allocation3 = null;
    allocation3 = new byte[4 * _1MB];
  }

}

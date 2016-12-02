package bo.jiang.core.jvm.memory;

import java.util.ArrayList;
import java.util.List;

/**
 * VM Args: -Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=.
 */
public class HeapOOM {
  static class OOMObject {}

  public static void main(String[] args) {
    List<OOMObject> list = new ArrayList<OOMObject>();

    while (true) {
      list.add(new OOMObject());
    }
  }
}

package bo.jiang.extend.log4j2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 按日志级别输出到不同的文件：
 * <Filters>
 *   <ThresholdFilter level="info" onMatch="DENY" onMismatch="NEUTRAL"/>
 *   <ThresholdFilter level="debug" onMatch="ACCEPT" onMismatch="DENY"/>
 * </Filters>
 * 根据匹配字符串输出到不同文件：
 * <RegexFilter regex=".* test .*" onMatch="ACCEPT" onMismatch="DENY"/>
 */
public class FilterDemo {
  private static Logger logger = LogManager.getLogger(FilterDemo.class);

  public static void main(String[] args) {
    logger.debug("test");
    logger.info("Hello test ");
    logger.error("Hello error");
  }
}

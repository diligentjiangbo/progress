package bo.jiang.extend.log4j2;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.FileAppender;
import org.apache.logging.log4j.core.appender.RollingFileAppender;
import org.apache.logging.log4j.core.appender.rolling.SizeBasedTriggeringPolicy;
import org.apache.logging.log4j.core.appender.rolling.TimeBasedTriggeringPolicy;
import org.apache.logging.log4j.core.appender.rolling.TriggeringPolicy;
import org.apache.logging.log4j.core.config.AppenderRef;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.layout.PatternLayout;

import java.util.concurrent.TimeUnit;

/**
 * 动态为每个任务创建配置文件
 */
public class DynamicDemo {

  public static void main(String[] args) throws InterruptedException {

    for (int i = 0; i < 5; i++) {
      Logger logger = DynamicDemo.createLogger(i);
      logger.info("Testing testing testing 111");
      logger.debug("Testing testing testing 222");
      logger.error("Testing testing testing 333");
      DynamicDemo.stop(i);
    }

  }

  public static void start(int jobId) {
    //为false时，返回多个LoggerContext对象，   true：返回唯一的单例LoggerContext
    final LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
    final Configuration config = ctx.getConfiguration();
    //创建一个展示的样式：PatternLayout，   还有其他的日志打印样式。
    String pattern = "%date{yyyy-MM-dd HH:mm:ss.SSS} [%level] [%thread] [%c{1.}:%L] - %msg%n";
    Layout layout = PatternLayout.createLayout(pattern,
        null, config, null, null, false, false, null, null);

    TriggeringPolicy tp = SizeBasedTriggeringPolicy.createPolicy("1KB");
    TimeBasedTriggeringPolicy timeBasedTp = TimeBasedTriggeringPolicy.createPolicy("10", "true");
    Appender appender = RollingFileAppender.createAppender(String.format("logs/test/sync-job-%s.log", jobId),
        "/logs/test/syncshows-job-%date{yyyy-MM-ddHHmmss}.log.zip",
        "true", "" + jobId, "true", null, "true", timeBasedTp, null, layout, null,
        null, "true", null, config);
    //日志打印方式——输出为文件
//    Appender appender = FileAppender.createAppender(
//        String.format("logs/test/syncshows-job-%s.log", jobId), "true", "false",
//        "" + jobId, null, "true", "true", null, layout, null, null, null, config);
    appender.start();
    config.addAppender(appender);
    AppenderRef ref = AppenderRef.createAppenderRef("" + jobId, null, null);
    AppenderRef[] refs = new AppenderRef[]{ref};
    LoggerConfig loggerConfig = LoggerConfig.createLogger(false, Level.ALL, "" + jobId,
        "true", refs, null, config, null);
    loggerConfig.addAppender(appender, null, null);
    config.addLogger("" + jobId, loggerConfig);
    ctx.updateLoggers();
  }

  public static void stop(int jobId) {
    final LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
    final Configuration config = ctx.getConfiguration();
    config.getAppender("" + jobId).stop();
    config.getLoggerConfig("" + jobId).removeAppender("" + jobId);
    config.removeLogger("" + jobId);
    ctx.updateLoggers();
  }

  /**
   * 获取Logger
   * <p>
   * 如果不想使用slf4j,那这里改成直接返回Log4j的Logger即可
   *
   * @param jobId
   * @return
   */
  public static Logger createLogger(int jobId) {
    start(jobId);
    return LogManager.getLogger("" + jobId);
  }
}

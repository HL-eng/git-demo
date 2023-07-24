import org.apache.log4j.Appender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.LogManager;
import org.junit.Test;

import org.apache.log4j.Logger;

public class TestLog4J {
    // 获取logger实列,获取日志记录器并读取配置文件,这个记录器将负责控制日志信息
    private static Logger logger = Logger.getLogger(TestLog4J.class);

    @Test
    public void testOutput(){
        // 记录debug级别的信息
        logger.debug("这是调试信息");
        // 记录info级别的信息
        logger.info("这是输出信息");
        // 记录error级别的信息
        logger.error("这是错误信息");
        // 记录warn级别的信息
        logger.warn("这是警告信息");
        // 记录trace级别的信息
        logger.trace("这是跟踪信息");
        // 记录fatal级别的信息
        logger.fatal("这是致命信息");
    }
}

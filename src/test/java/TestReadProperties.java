import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TestReadProperties {

    /**
     * 读取位于磁盘上面的配置文件
     */
    @Test
    public void test01(){
        String filePath = "G:/中科本原/config.properties";

        try (InputStream inputStream = new FileInputStream(filePath)) {
            Properties properties = new Properties();
            properties.load(inputStream);

            // 读取配置项
            String host = properties.getProperty("database.host");
            String port = properties.getProperty("database.port");
            String username = properties.getProperty("database.username");
            String password = properties.getProperty("database.password");
            String innerUrl = properties.getProperty("innerUrl");
            String loginUrl = properties.getProperty("loginUrl");

            // 打印配置项的值
            System.out.println("Database Host: " + host);
            System.out.println("Database Port: " + port);
            System.out.println("Database Username: " + username);
            System.out.println("Database Password: " + password);
            System.out.println("innerUrl: " + innerUrl);
            System.out.println("loginUrl: " + loginUrl);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

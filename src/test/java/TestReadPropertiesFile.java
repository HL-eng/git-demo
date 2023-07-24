import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TestReadPropertiesFile {
    public static void main(String[] args) {
        Properties prop = new Properties();
        InputStream input = null;

        try {
            input = new FileInputStream("G:\\中科本原\\TestDirectory\\config.properties");

            // 加载属性列表
            prop.load(input);

            // 获取属性值
            String innerIp = prop.getProperty("innerIp");
            String loginIp = prop.getProperty("loginIp");
            String urlPrefix = prop.getProperty("urlPrefix");

            // 输出属性值
            System.out.println("innerIp: " + innerIp);
            System.out.println("loginIp: " + loginIp);
            System.out.println("urlPrefix: " + urlPrefix);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}


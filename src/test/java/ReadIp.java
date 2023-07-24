import org.junit.Test;

import java.util.Calendar;
import java.util.ResourceBundle;

public class ReadIp {

    /**
     * 测试读取properties的内容
     */
    @Test
    public void testReadTest(){
        //读取根路径下面的资源配置文件，但是”config“前面不要加”/“
        ResourceBundle bundle = ResourceBundle.getBundle("config");
        String innerIp = bundle.getString("innerIp");
        System.out.println(innerIp);
    }

    /**
     *
     */
    @Test
    public void testConcatenateUrl(){
        ResourceBundle bundle = ResourceBundle.getBundle("config");
        String urlPrefix = bundle.getString("urlPrefix");
        String urlSuffix = bundle.getString("urlSuffix");
        System.out.println(urlPrefix+"--"+urlSuffix);
        Calendar calendar = Calendar.getInstance();
        //获取当前年
        int year = calendar.get(Calendar.YEAR);
        //获取当前月
        int month = calendar.get(Calendar.MONTH) + 1;
        System.out.println(year+"/"+month);

        //进行url的拼接
        String url = urlPrefix + year + "_" + "0" + month + "_" + "okr" + urlSuffix;
        System.out.println(url);
    }

    @Test
    public void test03(){
        ResourceBundle bundle = ResourceBundle.getBundle("config");
        System.out.println(bundle.getString("loginUrl"));
    }
}

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Test01 {

    @Test
    public void getFileName(){
        String path = "G:\\Test\\testZip\\李四-2023-07-okr.xlsx";
        File file = new File(path);
        String fileName = file.getName();
        //System.out.println(file.getName()); //结果李四-2023-07-okr.xlsx
        //fileName = fileName.split(".")[0];
        fileName = fileName.substring(0, fileName.lastIndexOf("."));
        System.out.println(fileName);
    }

    /**
     * 用于从excel表当中获取人员的姓名，然后还得与当前年-当前月-okr进行以下拼接
     */
    @Test
    public void getUserName(){
        String path = "G:\\Test\\testZip\\2023年30周工作周报-侯磊.xlsx";
        File file = new File(path);
        String fileName = file.getName();
        fileName = fileName.substring(fileName.lastIndexOf("-")+1,fileName.lastIndexOf("."));
        System.out.println(fileName);

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        String subject = fileName +"-"+year +"-"+ month + "-okr";//还需要判断，看看月份前面需不需要加上
    }

    @Test
    public void getFirstAndLastDayOfMonth(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        //获得本月第一天
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        String firstDay = sdf.format(calendar.getTime());
        System.out.println("firstDay:"+ firstDay);
        //获得本月最后一天
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        String lastDay = sdf.format(calendar.getTime());
        System.out.println(lastDay);
    }

    @Test
    public void test03(){
        try {
            // 1. 使用Jsoup连接到要解析的网页，并获取整个HTML文档
            Document doc = Jsoup.connect("http://example.com").get();

            // 2. 使用Jsoup的选择器语法找到所有class为“project child leaf public”的a标签
            Elements links = doc.select("a.project.child.leaf.public");

            // 3. 遍历找到的a标签，并输出它们的文本内容和链接地址
            for (Element link : links) {
                String linkText = link.text();
                //经过测试，这一条语句无法获得href地址
                //String linkUrl = link.absUrl("href");
                String linkUrl = link.attr("href"); //根据属性来获取连接地址是可以的
                System.out.println("Link Text: " + linkText);
                System.out.println("Link URL: " + linkUrl);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test04(){
    	//测试热修复方法
	System.out.println("hot-fix modified version1");
    }
}

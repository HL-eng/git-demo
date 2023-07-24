import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class ParseDom {

    @Test
    public void parseDom01() {
        String html = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>解析HTML</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <form id=\"query_form\" action=\"/projects/010-okr/issues\" accept-charset=\"UTF-8\" method=\"post\">\n" +
                "        <input type=\"hidden\" name=\"c[]\" value=\"id\" autocomplete=\"off\">\n" +
                "        <div class=\"autoscroll\">\n" +
                "            <table class=\"list issues\">\n" +
                "                <thead></thead>\n" +
                "                <tbody>\n" +
                "                    <tr id=\"issue-47\" class=\"hascontextmenu\">\n" +
                "                        <td class=\"checkbox\">\n" +
                "                            <input>sw</input>\n" +
                "                        </td>\n" +
                "                        <td class=\"id\">\n" +
                "                            <a href=\"/issues/47\">47</a>\n" +
                "                        </td>\n" +
                "                        <td class=\"subject\">\n" +
                "                            <a href=\"/issues/47\">测试上传周报信息</a>\n" +
                "                        </td>\n" +
                "                        <td class=\"assigned_to\">\n" +
                "                            <a class=\"user active\" href=\"/users/28\">侯磊</a>\n" +
                "                        </td>\n" +
                "                    </tr>\n" +
                "\n" +
                "                    <tr id=\"issue-34\" class=\"hascontextmenu\">\n" +
                "                        <td class=\"checkbox\">\n" +
                "                            <input>sw</input>\n" +
                "                        </td>\n" +
                "                        <td class=\"id\">\n" +
                "                            <a href=\"/issues/34\">34</a>\n" +
                "                        </td>\n" +
                "                        <td class=\"subject\">\n" +
                "                            <a href=\"/issues/34\">李童-2023-6-okr</a>\n" +
                "                        </td>\n" +
                "                        <td class=\"assigned_to\">\n" +
                "                            <a class=\"user active\" href=\"/users/28\">侯磊</a>\n" +
                "                        </td>\n" +
                "                    </tr>\n" +
                "\n" +
                "                    <tr id=\"issue-31\" class=\"hascontextmenu\">\n" +
                "                        <td class=\"checkbox\">\n" +
                "                            <input>sw</input>\n" +
                "                        </td>\n" +
                "                        <td class=\"id\">\n" +
                "                            <a href=\"/issues/31\">31</a>\n" +
                "                        </td>\n" +
                "                        <td class=\"subject\">\n" +
                "                            <a href=\"/issues/31\">王伟营-2023-6-okr</a>\n" +
                "                        </td>\n" +
                "                    </tr>\n" +
                "                </tbody>\n" +
                "            </table>\n" +
                "        </div>\n" +
                "    </form>\n" +
                "</body>\n" +
                "</html>";
        ParseDom parseDom = new ParseDom();
        //这一块是使用下面的方法进行测试的
        //Map<String, String> nameAndUrl = parseDom.getNameAndUrl(html);

        //现在使用自己的工具类进行测试
        //com.fdmtek.utils.ParseDom.parseDom();

    }

    public Map<String,String> getNameAndUrl(String html){
        //定义好一个Map，将对应的姓名和url进行封装，然后返回
        Map<String,String> nameUrl = new HashMap<>();
        //将html转为dom对象
        Document doc = Jsoup.parse(html);
        //System.out.println(doc);

        //然后根据doc,找出其中的tr标签下面的class为subject的td标签，或者看看可不可以直接根据class为subject找到td标签
        Elements elements = doc.select(".subject");//现在已经找到了class为subject的所有tr标签
        String innerIp = "193.168.51.21";

        for (Element ele: elements) {
            //System.out.println(ele.children());//找到了对应的a标签，这样我再根据a标签找到属性下面的href和内容
            String href = ele.children().attr("href");//这个获取的是url


            href = innerIp + href;
            //String text = ele.children().text();//这个获取的是文本内容
            //对text进行一下划分，只保留姓名
            //text = text.split("-")[0];
            String name = ele.children().text().split("-")[0];
            System.out.println(name+":"+href);
            nameUrl.put(name,href);
            //System.out.println(ele);
        }
        //System.out.println(nameUrl);
        return nameUrl;
    }
}

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

public class TestParseDomToReadId {

    @Test
    public void parseDomTest01(){
        String html = "<html>\n" +
                "<body>\n" +
                "\n" +
                "<select id=\"mySelect\">\n" +
                "  <option value=\"1\">Option 1</option>\n" +
                "  <option value=\"2\">Option 2</option>\n" +
                "  <option value=\"3\">Option 3</option>\n" +
                "  <option value=\"4\">Option 4</option>\n" +
                "</select>\n" +
                "\n" +
                "</body>\n" +
                "</html>";

        Document doc = Jsoup.parse(html);
        Element selectElement = doc.getElementById("mySelect");

        Elements options = selectElement.getElementsByTag("option");
        for (Element option : options) { //这个地方看看把Option值前面加上空格&nbsp;&nbsp看看出来的是什么？
            if(option.text().contains("Option 4")){
                String value = option.attr("value");
                System.out.println("Option 4 对应的 value 值为: " + value);
                break;
            };

            if (option.text().equals("Option 4")) {
                String value = option.attr("value");
                System.out.println("Option 4 对应的 value 值为: " + value);
                break;
            }
        }
    }

    @Test
    public void parseDomTest02(){
        String html = "<html><body><table>" +
                "<tr><td class=\"subject\">Subject 1</td><td>Value 1</td></tr>" +
                "<tr><td class=\"subject\">Subject 2</td><td>Value 2</td></tr>" +
                "<tr><td>Not a subject</td><td>Value 3</td></tr>" +
                "</table></body></html>";

        // 解析HTML文档
        Document doc = Jsoup.parse(html);

        // 查找所有class为"subject"的td标签
        Elements subjectTds = doc.select("td.subject");

        // 遍历并打印结果
        for (Element td : subjectTds) {
            System.out.println(td.text());
        }
    }
}

package com.zkby;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


public class HttpClient {
    @Test
    public void testGet() throws Exception {
        //1.创建HttpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();

        //2. 创建HttpGet请求，并进行相关设置
        HttpGet httpGet = new HttpGet("https://www.kugou.com/?username==java");
        //HttpGet httpGet = new HttpGet("http://192.168.51.21/projects/010-okr/issues?set_filter&tracker_id=1");
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.121 Mobile Safari/537.36 Edg/85.0.564.68");

        //3.发起请求
        CloseableHttpResponse response = httpClient.execute(httpGet);
        String html = "";
        //4.判断响应状态码并获取响应数据
        if (response.getStatusLine().getStatusCode() == 200) { //200表示响应成功
            html = EntityUtils.toString(response.getEntity(), "UTF-8");
            System.out.println(html);
        }
        //先将字符串封装成Document对象，再根据Document对象，解析出a标签来，具体地说，应该是<tbody>下面的a标签
        Document doc = Jsoup.parse(html);
        //然后我再这个doc标签下面找到<tbody>标签下面的<a>标签




        //根据姓名我能够拿到其对应的url,然后我再转到对应的url页面当中，进入编辑页面

        //获取解压到的文件名，以‘-’进行字符串的切分，拿到第一个值，这也就是姓名值
        //或者是直接不用切分，直接利用文件名来拿对应的url，以方便的转到相应的页面中，然后再点击编辑页面，上传到服务器


        //进入编辑页面之后，需要了解这个页面结构，也就是form表单当中有什么信息，你需要以form表单当中的数据作为依据，将解压好的文件上传到redmine服务器

        //


        //5.关闭资源
        httpClient.close();
        response.close();

    }}
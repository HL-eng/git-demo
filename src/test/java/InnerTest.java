import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class InnerTest {
    public static void main(String[] args) throws IOException {
        String url = "http://192.168.51.21/login";

        //此处先获得authenticity_token的值
        // 第一次请求，获取登录页面的HTML
        Document loginPage = Jsoup.connect(url).get();

        // 从登录页面中获取authenticity_token的值
        String authenticityToken = extractAuthenticityToken(loginPage);

        List<NameValuePair> formData = new ArrayList<>();
        formData.add(new BasicNameValuePair("utf8", "✓"));
        formData.add(new BasicNameValuePair("authenticity_token", authenticityToken));
        formData.add(new BasicNameValuePair("username", "houlei"));
        formData.add(new BasicNameValuePair("password", "111111"));
        formData.add(new BasicNameValuePair("login", "登录"));

        try {
            sendPostRequest(url, formData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendPostRequest(String url, List<NameValuePair> formData) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);

        //先发起一个Get请求，得到这个Session值
        HttpGet request = new HttpGet(url);
        HttpResponse httpResponse = httpClient.execute(request);
        //需要从这个响应头里面得到这个session值
        Header[] headers = httpResponse.getHeaders("Set-Cookie");
        String sessionId = null;
        for(Header header : headers){
            String name = header.getName();
            String value = header.getValue();
            sessionId = value.split(";")[0];
        }
        System.out.println(sessionId);


        // 对表单数据进行URL编码
        List<NameValuePair> encodedFormData = new ArrayList<>();
        for (NameValuePair pair : formData) {
            String encodedName = URLEncoder.encode(pair.getName(), StandardCharsets.UTF_8.toString());
            String encodedValue = URLEncoder.encode(pair.getValue(), StandardCharsets.UTF_8.toString());
            encodedFormData.add(new BasicNameValuePair(encodedName, encodedValue));
        }

        // 设置请求参数
        httpPost.setEntity(new UrlEncodedFormEntity(encodedFormData, StandardCharsets.UTF_8));


        // 添加请求头信息
        httpPost.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7");
        httpPost.setHeader("Accept-Encoding", "gzip, deflate");
        httpPost.setHeader("Accept-Language", "zh-CN,zh;q=0.9,en-US;q=0.8,en;q=0.7");
        httpPost.setHeader("Cache-Control", "max-age=0");
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
        httpPost.setHeader("Cookie", sessionId);
        httpPost.setHeader("Host", "192.168.51.21");
        httpPost.setHeader("Origin", "http://192.168.51.21");
        httpPost.setHeader("Proxy-Connection", "keep-alive");
        httpPost.setHeader("Referer", "http://192.168.51.21/login");
        httpPost.setHeader("Upgrade-Insecure-Requests", "1");
        httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36");

        // 发送请求
        HttpResponse response = httpClient.execute(httpPost);

        // 获取响应内容
        HttpEntity entity = response.getEntity();
        String responseBody = EntityUtils.toString(entity);

        // 打印响应信息
        System.out.println("Response Code: " + response.getStatusLine().getStatusCode());
        System.out.println("Response Body: " + responseBody);

        // 关闭连接
        httpClient.close();
    }

    private static String extractAuthenticityToken(Document doc) {
        Element authenticityTokenElement = doc.selectFirst("input[name=authenticity_token]");
        return authenticityTokenElement.val();
    }
}


import com.fdmtek.utils.LoginReturnCode;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TestLoginWithSession {

    @Test
    public void testLoginWithSession(){
        //向服务器端提交验证请求，
        ResourceBundle bundle = ResourceBundle.getBundle("config");
        String testLoginIp = bundle.getString("testLoginIp");
        HttpClient httpCLient = HttpClientBuilder.create().build();

        HttpPost httpPost = new HttpPost(testLoginIp);
        try {
            //构建表单参数
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("username","houlei"));
            params.add(new BasicNameValuePair("password","123456"));
            //将表单参数设置到请求中
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            //发送请求并获取响应
            HttpResponse response = httpCLient.execute(httpPost);
            //处理响应 这个地方还得拿到session值，这样还得把这个session值传给后面的上传文件的类或者方法 如果你还想返回response对象，你可以封装成Map进行返回
            //因为Java不支持返回多个对象，其他的我只需要将response对象设为空即可，最后在前面判断response是不是为空，不为空说明就是登录成功，且拿到response对象了
            //后面只需要对这个response对象进行判断操作就行了
            Header[] headers = response.getHeaders("Set-Cookie");
            CookieStore cookieStore = new BasicCookieStore();
            for(Header header : headers){
                String value = header.getValue();
                BasicClientCookie cookie = new BasicClientCookie("CookieName", value);
                cookieStore.addCookie(cookie);
            }
            int statusCode = response.getStatusLine().getStatusCode();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void testAccessMainJsp() throws IOException {
        //向服务器端提交验证请求，
        ResourceBundle bundle = ResourceBundle.getBundle("config");
        String testMainIp = bundle.getString("testMainIp");
        HttpClient httpCLient = HttpClientBuilder.create().build();
        //HttpGet httpGet = new HttpGet("https://www.baidu.com");
        HttpGet httpGet = new HttpGet(testMainIp);
        HttpResponse response = httpCLient.execute(httpGet);
        //System.out.println(response);
        String responseString = EntityUtils.toString(response.getEntity());//直接获得页面的响应内容
        System.out.println(responseString);//这样就会跳转到登录界面，所以再写一个测试方法，让这个请求main.jsp的方法带上session信息
        //要写两个，其中一个是Get请求携带Session信息，另外一个是Post请求携带Session信息的方法
    }

    /**
     * 这个方法是先模拟登录拿到session信息，然后发送Get请求给main.jsp看看能不能正常访问首页
     * 最好是这个Get请求不仅携带着Session还有其他参数
     */
    @Test
    public void sendGetWithSession(){
        //向服务器端提交验证请求，
        ResourceBundle bundle = ResourceBundle.getBundle("config");
        String testLoginIp = bundle.getString("testLoginIp");
        ResourceBundle bundle2 = ResourceBundle.getBundle("config");
        String testMainIp = bundle.getString("testMainIp");
        HttpClient httpCLient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(testLoginIp);
        try {
            //构建表单参数
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("username","houlei"));
            params.add(new BasicNameValuePair("password","123456"));
            //将表单参数设置到请求中
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            //发送请求并获取响应
            HttpResponse response = httpCLient.execute(httpPost);
            //处理响应 这个地方还得拿到session值，这样还得把这个session值传给后面的上传文件的类或者方法 如果你还想返回response对象，你可以封装成Map进行返回
            //因为Java不支持返回多个对象，其他的我只需要将response对象设为空即可，最后在前面判断response是不是为空，不为空说明就是登录成功，且拿到response对象了
            //后面只需要对这个response对象进行判断操作就行了
            Header[] headers = response.getHeaders("Set-Cookie");
            CookieStore cookieStore = new BasicCookieStore();
            //CloseableHttpClient HttpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
            for(Header header : headers){
                String name = header.getValue().split(";")[0].split("=")[0];
                String value = header.getValue().split(";")[0].split("=")[1];
                //试试这里的name和value是不是就是页面上的Name和Value
                BasicClientCookie cookie = new BasicClientCookie(name, value);//感觉这个地方不对
                cookie.setPath("/oa");
                cookie.setDomain("http://127.0.0.1:8080");
                //cookie.setExpiryDate();
                cookieStore.addCookie(cookie);
            }
            // 设置HttpContext，以便在后续请求中携带Cookie和Session信息
            HttpClientContext context = HttpClientContext.create();
            context.setCookieStore(cookieStore);
            //相当于拿到Session信息了，此时再发送Get请求
            HttpGet request = new HttpGet(testMainIp);
            HttpResponse httpResponse = httpCLient.execute(request, context);
            //HttpResponse httpResponse = HttpClient.execute(request);
            String responseString = EntityUtils.toString(httpResponse.getEntity());//直接获得页面的响应内容
            System.out.println(responseString);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 这个方法是先模拟登录拿到session信息，然后发送Post请求给main.jsp看看能不能正常访问首页
     */
    @Test
    public void sendPostWithSession(){
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            // 创建CookieStore和HttpContext
            CookieStore cookieStore = new BasicCookieStore();
            HttpClientContext context = HttpClientContext.create();
            context.setCookieStore(cookieStore);

            // 发送第一个Post请求
            HttpPost firstPost = new HttpPost("http://localhost:8080/oa/login");

            //构建表单参数
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("username","houlei"));
            params.add(new BasicNameValuePair("password","111111"));
            //将表单参数设置到请求中
            firstPost.setEntity(new UrlEncodedFormEntity(params));

            HttpResponse firstResponse = httpClient.execute(firstPost, context);

            // 处理第一个GET响应
            // ...

            // 获取Session信息
            List<Cookie> cookies = cookieStore.getCookies();
            String sessionID = null;
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("JSESSIONID")) { //换成内网之后这个地方要修改成“_red_mine”,这个是SessionID的名字
                    sessionID = cookie.getValue();
                    break;
                }
            }
            // 发送第二个GET请求并携带Session信息
            HttpPost secondPost = new HttpPost("http://localhost:8080/oa/submit");
            secondPost.addHeader("Cookie", "JSESSIONID=" + sessionID);
            //添加请求参数
            List<NameValuePair> params2 = new ArrayList<>();
            params2.add(new BasicNameValuePair("param1","houlei"));
            params2.add(new BasicNameValuePair("param2","123456"));
            //将表单参数设置到请求中
            secondPost.setEntity(new UrlEncodedFormEntity(params2));
            HttpResponse secondResponse = httpClient.execute(secondPost);

            // 处理第二个GET响应
            // 查看第二个响应的内容
            String responseString = EntityUtils.toString(secondResponse.getEntity());//直接获得页面的响应内容
            System.out.println(responseString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void httpClientWithSessionExample(){
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            // 创建CookieStore和HttpContext
            CookieStore cookieStore = new BasicCookieStore();
            HttpClientContext context = HttpClientContext.create();



            context.setCookieStore(cookieStore);

            // 发送第一个Post请求
            HttpPost firstPost = new HttpPost("http://localhost:8080/oa/login");

            //构建表单参数
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("username","houlei"));
            params.add(new BasicNameValuePair("password","123456"));
            //将表单参数设置到请求中
            firstPost.setEntity(new UrlEncodedFormEntity(params));

            HttpResponse firstResponse = httpClient.execute(firstPost, context);
            int code = firstResponse.getStatusLine().getStatusCode();
            System.out.println(code);
            // 处理第一个GET响应
            // ...

            // 获取Session信息
            List<Cookie> cookies = cookieStore.getCookies();
            String sessionID = null;
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("JSESSIONID")) {
                    sessionID = cookie.getValue();
                    break;
                }
            }
            // 发送第二个GET请求并携带Session信息
            HttpGet secondGet = new HttpGet("http://localhost:8080/oa/main.jsp");
            secondGet.addHeader("Cookie", "JSESSIONID=" + sessionID);

            HttpResponse secondResponse = httpClient.execute(secondGet);

            // 处理第二个GET响应
            // 查看第二个响应的内容
            String responseString = EntityUtils.toString(secondResponse.getEntity());//直接获得页面的响应内容
            System.out.println(responseString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 思路：我们应该先请求一下登录界面，然后把页面内容给解析一下，然后获得autnenticity_token的值
     * 之后带着这个值，将其封装到form表单当中，进行请求
     */

    @Test
    public void testCreateHttpClient() throws IOException {
        //主页面的url
        String ipString = "http://192.168.51.21/login";
        //直接解析一下这个页面
        // 第一次请求，获取登录页面的HTML
        Document loginPage = Jsoup.connect(ipString).get();

        // 从登录页面中获取authenticity_token的值
        String authenticityToken = extractAuthenticityToken(loginPage);
        //System.out.println(authenticityToken);

        //发起HttpClient请求
        HttpClient httpCLient = HttpClientBuilder.create().build();

        //先发起一个Get请求，得到这个Session值
        HttpGet request = new HttpGet(ipString);
        HttpResponse httpResponse = httpCLient.execute(request);
        //需要从这个响应头里面得到这个session值
        Header[] headers = httpResponse.getHeaders("Set-Cookie");
        String sessionId = null;
        String sessionName = null;
        for(Header header : headers){
            String name = header.getName();
            String value = header.getValue();
            sessionName = value.split(";")[0].split("=")[0];
            sessionId = value.split(";")[0].split("=")[1];
        }
        System.out.println(sessionId);

        HttpPost httpPost = new HttpPost(ipString);//创建Post请求
        httpPost.addHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7");
        httpPost.addHeader("Accept-Encoding","gzip, deflate");
        httpPost.addHeader("Accept-Language","zh-CN,zh;q=0.9,en-US;q=0.8,en;q=0.7");
        httpPost.addHeader("Cache-Control","max-age=0");
        //httpPost.addHeader("Content-Length","218");
        httpPost.addHeader("Content-Type","application/x-www-form-urlencoded");
        httpPost.addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36");
        httpPost.addHeader("Upgrade-Insecure-Requests","1");
        httpPost.addHeader("Host","192.168.51.21");
        httpPost.addHeader("Proxy-Connection","keep-alive");
        httpPost.addHeader("Cookie", sessionName+"="+sessionId);
        //提交参数，查看返回的状态码
        //构建表单参数
        List<NameValuePair> params = new ArrayList<>();
        //params.add(new BasicNameValuePair("utf8","%E2%9C%93"));
        //params.add(new BasicNameValuePair("utf8","√"));
        params.add(new BasicNameValuePair("authenticity_token",authenticityToken));
        params.add(new BasicNameValuePair("username","houlei"));
        params.add(new BasicNameValuePair("password","111111"));
        //params.add(new BasicNameValuePair("login","%E7%99%BB%E5%BD%95"));
        //params.add(new BasicNameValuePair("login","登录"));

        //将表单参数设置到请求当中
        httpPost.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
        HttpResponse response = httpCLient.execute(httpPost);
        int code = response.getStatusLine().getStatusCode();
        System.out.println(code);

    }


    private String extractAuthenticityToken(Document doc) {
        Element authenticityTokenElement = doc.selectFirst("input[name=authenticity_token]");
        return authenticityTokenElement.val();
    }

    /**
     * //发起HttpClient请求
     *         HttpClient httpCLient = HttpClientBuilder.create().build();
     *         //创建CookieStore和HttpContext
     *         BasicCookieStore cookieStore = new BasicCookieStore();
     *         HttpClientContext context = HttpClientContext.create();
     *         context.setCookieStore(cookieStore);
     *
     *         HttpPost httpPost = new HttpPost(ipString);//创建Post请求
     *         httpPost.addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36");
     *         httpPost.addHeader("Upgrade-Insecure-Requests","1");
     *
     *         //提交参数，查看返回的状态码
     *         //构建表单参数
     *         List<NameValuePair> params = new ArrayList<>();
     *         params.add(new BasicNameValuePair("utf8","%E2%9C%93"));
     *         params.add(new BasicNameValuePair("authenticity_token",authenticityToken));
     *         params.add(new BasicNameValuePair("username","houlei"));
     *         params.add(new BasicNameValuePair("password","111111"));
     *         params.add(new BasicNameValuePair("login","%E7%99%BB%E5%BD%95"));
     *
     *         //将表单参数设置到请求当中
     *         httpPost.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
     *         HttpResponse response = httpCLient.execute(httpPost,context);
     *         int code = response.getStatusLine().getStatusCode();
     *         System.out.println(code);
     *     }
     */
}

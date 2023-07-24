package com.fdmtek.login;

import com.fdmtek.utils.LoginReturnCode;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.*;

public class Login {

    //定义一个Map<>集合，里面有两部分信息，一个是登录返回的状态码，另外一个就是session信息
    Map<String,String> returnCodeStringMap = new HashMap<>();

    public Map<String,String> login(String username, String password){
        //验证用户名和密码不能为空
        if (username == null || "".equals(username)){
            String loginCode =  LoginReturnCode.USERNAME_IS_EMPTY.getCode().toString();
            returnCodeStringMap.put("loginCode",loginCode);
            returnCodeStringMap.put("sessionID",null);
            return returnCodeStringMap;
        }
        if(password == null || "".equals(password)){
            String loginCode =  LoginReturnCode.USERNAME_IS_EMPTY.getCode().toString();
            returnCodeStringMap.put("loginCode",loginCode);
            returnCodeStringMap.put("sessionID",null);
            return returnCodeStringMap;
        }
        /**
         * 主业务逻辑代码
         * 1：先从资源文件当中读取到 自己的登录IP地址
         * 2：进行登录验证的请求
         */
        //向服务器端提交验证请求，
        ResourceBundle bundle = ResourceBundle.getBundle("config");
        String testLoginIp = bundle.getString("testLoginIp");

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            // 创建CookieStore和HttpContext
            CookieStore cookieStore = new BasicCookieStore();
            HttpClientContext context = HttpClientContext.create();
            context.setCookieStore(cookieStore);

            // 发送第一个Post请求
            HttpPost firstPost = new HttpPost(testLoginIp);

            //构建表单参数
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("username","houlei"));
            params.add(new BasicNameValuePair("password","123456"));
            //将表单参数设置到请求中
            firstPost.setEntity(new UrlEncodedFormEntity(params));

            HttpResponse firstResponse = httpClient.execute(firstPost, context);

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

            String loginCode =  LoginReturnCode.LOGIN_SUCCESS.getCode().toString();
            returnCodeStringMap.put("loginCode",loginCode);
            returnCodeStringMap.put("sessionID",sessionID);
            return returnCodeStringMap;

        } catch (IOException e) {
            e.printStackTrace();
        }

        String loginCode = LoginReturnCode.LOGIN_FAIL.getCode().toString();
        returnCodeStringMap.put("loginCode",loginCode);
        returnCodeStringMap.put("sessionID",null);
        return returnCodeStringMap;
    }
}

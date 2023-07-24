package com.fdmtek.createproject;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CreateProject {
    //@Test
    public void testCreateProject(String sessionID) throws IOException {
        /**
         * 这一步其实是需要首先做的，因为你只有做了这一步才能进行下面的excel表格等数据的提交
         * 1：请求创建项目的url，
         * 2：然后会给你返回一个创建项目的页面，
         * 3：对其中的表单信息进行填写
         * 4：之后再进行提交
         */
        //向服务器端提交验证请求，
        ResourceBundle bundle = ResourceBundle.getBundle("config");
        String testLoginIp = bundle.getString("testProjectIp");
        HttpClient httpCLient = HttpClientBuilder.create().build();

        HttpGet httpGet = new HttpGet(testLoginIp);
        httpGet.addHeader("Cookie", "JSESSIONID=" + sessionID);
        HttpResponse response = httpCLient.execute(httpGet);

        //查看响应的内容
        String responseString = EntityUtils.toString(response.getEntity());//直接获得页面的响应内容
        System.out.println(responseString);

        //获取其中的表单信息内容，填写
        HttpPost httpPost = new HttpPost("http://localhost:8080/oa/createProject");
        httpGet.addHeader("Cookie", "JSESSIONID=" + sessionID);
        //构建表单参数
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("projectName","2023_07_OKR"));
        params.add(new BasicNameValuePair("projectDescription","2023_07_OKR"));
        //将表单参数设置到请求中
        httpPost.setEntity(new UrlEncodedFormEntity(params));
        //发送请求并获取响应
        HttpResponse response2 = httpCLient.execute(httpPost);
        //将form表单的内容提交到对应的地方
        int statusCode = response2.getStatusLine().getStatusCode();
        System.out.println(statusCode);
    }

    /**
     * 这一块创建问题的程序和上面的程序基本上是类似的
     */
    @Test
    public void testCreateProblem(){

    }
}

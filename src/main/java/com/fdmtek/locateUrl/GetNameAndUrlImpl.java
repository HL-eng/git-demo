package com.fdmtek.locateUrl;

import com.fdmtek.utils.ParseDom;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class GetNameAndUrlImpl implements GetNameAndUrl {

    /**
     * 具体地寻找每一个姓名和与其对应的url
     * @param html 传入的内容是web页面的字符串纯内容
     * @return 返回每一个用户与其ip的对应Map映射关系
     */
    @Override
    public Map<String,String> getNameAndUrlByHtml(String html){
        //将html转为dom对象
        Document doc = Jsoup.parse(html);
        //直接读取内网IP
        ResourceBundle bundle = ResourceBundle.getBundle("config");
        String innerIp = bundle.getString("innerIp");
        Map<String, String> nameUrl = ParseDom.parseDom(innerIp, doc);//这个地方的ip最好定义的properties文件当中
        return nameUrl;
    }
    /**
     * 正式使用的使用这个方法，上面的方法仅用于测试使用
     * @param ip
     * @return
     */
    @Override
    public Map<String, String> getNameAndUrlByIp(String ip) {
        Map<String, String> nameUrl = new HashMap<>();
        //先根据ip地址进行解析页面，有异常信息，直接捕捉
        try {
            Document doc = Jsoup.connect(ip).get();
            //直接读取内网IP
            ResourceBundle bundle = ResourceBundle.getBundle("config");
            String innerIp = bundle.getString("innerIp");
            nameUrl = ParseDom.parseDom(innerIp, doc);//这个地方的ip最好定义的properties文件当中
        }catch (IOException e){
            e.printStackTrace();
        }
        return nameUrl;
    }
}

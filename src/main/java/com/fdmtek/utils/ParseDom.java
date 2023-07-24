package com.fdmtek.utils;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.Map;

public class ParseDom {

    //私有化构造方法
    private ParseDom(){};

    //用于解析页面的工具类方法
    public static Map<String,String> parseDom(String innerIp, Document doc){
        //定义好一个Map，将对应的姓名和url进行封装，然后返回
        Map<String,String> nameUrl = new HashMap<>();
        //然后根据doc,找出其中的tr标签下面的class为subject的td标签，或者看看可不可以直接根据class为subject找到td标签
        Elements elements = doc.select(".subject");//现在已经找到了class为subject的所有tr标签

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
        return nameUrl;
    }
}

package com.zkby;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class HtmlParserExample {
    public static void main(String[] args) {
        String url = "http://example.com"; // HTML页面的URL

        try {
            Document doc = Jsoup.connect(url).get();

            // 获取所有的<a>标签
            Elements links = doc.select("a");
            for (Element link : links) {
                String linkText = link.text();
                String linkUrl = link.attr("href");
                System.out.println("链接文本：" + linkText);
                System.out.println("链接URL：" + linkUrl);
            }

            // 获取所有的<img>标签
            Elements images = doc.select("img");
            for (Element image : images) {
                String imageUrl = image.attr("src");
                System.out.println("图片URL：" + imageUrl);
            }

            // 获取特定CSS类的元素
            Elements elements = doc.select(".my-class");
            for (Element element : elements) {
                // 处理特定CSS类的元素
            }

            // 其他解析和处理操作...
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

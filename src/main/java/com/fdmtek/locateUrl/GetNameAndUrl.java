package com.fdmtek.locateUrl;

import java.util.Map;

public interface GetNameAndUrl {

    //直接根据html的内容得到每一个用户和其url的对应关系
    Map<String,String> getNameAndUrlByHtml(String html);

    //根据ip地址来获得每一个用户和其url的对应关系
    Map<String,String> getNameAndUrlByIp(String ip);
}

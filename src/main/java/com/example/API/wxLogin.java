package com.example.API;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.Map;

import static Util.MyUtil.getPostData;

public class wxLogin {
    static void WXLogin (HttpExchange exchange) throws IOException {
        System.out.println("进入WXLogin方法");
        String code = getPostData(exchange).get("code");
        System.out.println(code);
        if (!"null".equals(code)) {
            System.out.println("不存在");
        }
    }
}

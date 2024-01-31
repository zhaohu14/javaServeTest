package com.example.API;

import Util.HttpRequestSender;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.Map;

import static Util.MyUtil.*;
import static Util.config.*;
import com.google.gson.JsonObject;

public class wxLogin {
    static void WXLogin (HttpExchange exchange) throws IOException {
        String code = getPostData(exchange).get("code");
//        String body = HttpRequestSender.sendGetRequest(wxConfig.getAccessTokenUrl());
//        System.out.println(body);
//        Map<String, String> data = parsePostData(body, exchange);
//        System.out.println(data.get("expires_in"));
//        if (!"7200".equals(data.get("expires_in"))) {
//            throwException(exchange, data.get("errmsg"));
//        }
//        if ("null".equals(code)) {
//            throwException(exchange, "code参数不合法");
//            return;
//        }

        String url = wxConfig.getCode2Session(code).replace("\"", "");
        String body = HttpRequestSender.sendGetRequest(url);
        System.out.println(body);

        Map<String, String> data = parsePostData(body, exchange);
        if ("40029".equals(data.get("errcode"))) {
            throwException(exchange, data.get("errmsg"));
            return;
        }
        JsonObject jsonObj = new JsonObject();
        jsonObj.addProperty("openid", data.get("openid"));
        successException(exchange, jsonObj);
    }
}

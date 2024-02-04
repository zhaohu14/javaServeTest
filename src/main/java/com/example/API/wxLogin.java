package com.example.API;

import Util.HttpRequestSender;
import Util.MyUtil;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;
import mysql.DatabaseConnector;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import static Util.MyUtil.*;
import static Util.config.wxConfig;

public class wxLogin {
    static void WXLogin (HttpExchange exchange) throws IOException {
        String code = getPostData(exchange).get("code");

        String url = wxConfig.getCode2Session(code).replace("\"", "");
        String body = HttpRequestSender.sendGetRequest(url);

        Map<String, String> data = parsePostData(body, exchange);
        if ("40029".equals(data.get("errcode"))) {
            throwException(exchange, data.get("errmsg"));
            return;
        }
//        JsonObject jsonObj = new JsonObject();
//        jsonObj.addProperty("openid", data.get("openid"));
//        successException(exchange, jsonObj);
        String openid = data.get("openid");
        String uuid = MyUtil.encryptString(openid);
        System.out.println(uuid);
        DatabaseConnector connector = new DatabaseConnector();
        String query = "SELECT * FROM user_table WHERE openid=" + openid;
        query = query.replace("\"", "'");
        ResultSet resultSet = connector.executeQuery(query);

        try {
            boolean result = false;
            if (resultSet.next()) {
                // 走更新数据
                System.out.println("获取的数据：" + resultSet.getString("openid"));
                result = updataUuid(connector, openid, uuid);

//                boolean result =
            } else {
                System.out.println("无数据走插入数据");
                result = addDataUuid(connector, openid, uuid);
            }
            if (result) {
                JsonObject jsonObj = new JsonObject();
                jsonObj.addProperty("Authorization", uuid);
                successException(exchange, jsonObj);
            } else {
                throwException(exchange, "插入或更新数据失败，请联系管理员！");
            }
//            if (resultSet.getString("openid")) {
//
//            }
        } catch (SQLException e) {
            System.out.println("获取数据异常：" + e);
        }

        // 关闭数据库连接
        connector.closeConnection();

    }
    static boolean updataUuid (DatabaseConnector connector, String openid, String uuid) {
//        String updateQuery = ("UPDATE  SET uuid = " + uuid +  " WHERE openid = " + openid).replace("\"", "'");
        String updateQuery = "UPDATE user_table SET uuid = ? WHERE openid = ?";
        System.out.println("更新数据库语句：" + updateQuery);
        boolean result = connector.executeUpdata(updateQuery, uuid, openid.replace("\"", ""));
        return result;
    }
    static boolean addDataUuid (DatabaseConnector connector, String openid, String uuid) {
        String addDATE = "INSERT INTO user_table (openid, uuid) VALUES (?, ?)";
        boolean result = connector.addUpdataSql(addDATE, openid.replace("\"", ""), uuid);
        return result;
    }
}

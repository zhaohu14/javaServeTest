package Util;
import com.google.gson.JsonElement;
import com.sun.net.httpserver.HttpExchange;
import org.json.JSONObject;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.google.gson.Gson;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MyUtil {
    /*
    * 获取POST 请求参数
    * @exchange: 请求体 HttpExchange
    * */
    public static Map<String, String> getPostData(HttpExchange exchange) throws IOException {
        String requestMethod = exchange.getRequestMethod();
        Map<String, String> dataMap = new HashMap<>();

        // 确保是POST请求
        if ("POST".equals(requestMethod)) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody()))) {
                StringBuilder postData = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    postData.append(line);
                }
                dataMap = parsePostData(postData.toString(), exchange);
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
//        System.out.println(dataMap);
        return dataMap;
    }

    /*
    * 解析JSON数据
    * @postData：请求参数 Map<String, String>
    * */
    public static Map<String, String> parsePostData(String postData, HttpExchange exchange) throws IOException {

        Map<String, String> dataMap = new HashMap<>();

        // 假设POST数据是JSON格式，使用JSON解析库解析
//        try {
//            Gson gson = new Gson();
//            JSONObject jsonObject = new JSONObject(postData);
//            for (String key : jsonObject.keySet()) {
//                dataMap.put(key, jsonObject.getString(key));
//            }
//        } catch (Exception e) {
//            throwException(exchange, e.getMessage());
//        }
        try {
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(postData, JsonObject.class);

            for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
                dataMap.put(entry.getKey(), entry.getValue().toString());
            }
        } catch (Exception e) {
            throwException(exchange, e.getMessage());
        }

        return dataMap;
    }

    /*
    * 给前端返回请求异常
    * @exchange：请求体 HttpExchange
    * @errorMsg: 请求错误信息 String
    * */
    public static void throwException (HttpExchange exchange, String errorMsg) throws  IOException {
        JsonObject jsonResponse = new JsonObject();
        jsonResponse.addProperty("msg", errorMsg);
        jsonResponse.addProperty("state", "fail");
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        exchange.sendResponseHeaders(200, 0);
        try {
            OutputStream os = exchange.getResponseBody();
            // 使用 Gson 的 JsonWriter 直接将 JSON 对象写入输出流
            try (JsonWriter writer = new JsonWriter(new OutputStreamWriter(os, "UTF-8"))) {
                writer.jsonValue(jsonResponse.toString());
            }
        } catch (Exception err) {
            err.printStackTrace();
        };
        exchange.close();
    }
    /*
    * 返回成功响应
    * @exchange: 请求体 HttpExchange
    * @obj: 返回前端JSON数据 JsonObject
    * */
    public static void successException (HttpExchange exchange, JsonObject obj) throws IOException {
        obj.addProperty("state", "ok");
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        exchange.sendResponseHeaders(200, 0);
        try {
            OutputStream os = exchange.getResponseBody();
            // 使用 Gson 的 JsonWriter 直接将 JSON 对象写入输出流
            try (JsonWriter writer = new JsonWriter(new OutputStreamWriter(os, "UTF-8"))) {
                writer.jsonValue(obj.toString());
            }
        } catch (Exception err) {
            err.printStackTrace();
        };
        exchange.close();
    }

    /*
    * md5加密
    * @str: 需要加密的字符串 String
    * */
    public static String encryptString(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());

            byte[] byteData = md.digest();

            // 将字节数组转换为十六进制字符串
            StringBuilder hexString = new StringBuilder();
            for (byte b : byteData) {
                String hex = Integer.toHexString(0xFF & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            // 处理加密算法不可用的异常
            return null;
        }
    }
}

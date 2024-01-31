package Util;

import com.sun.net.httpserver.HttpExchange;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

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
        System.out.println(dataMap);
        return dataMap;
    }

    /*
    * 解析JSON数据
    * @postData：post请求参数 Map<String, String>
    * */
    private static Map<String, String> parsePostData(String postData, HttpExchange exchange) throws IOException {

        Map<String, String> dataMap = new HashMap<>();

        // 假设POST数据是JSON格式，使用JSON解析库解析
        try {
            JSONObject jsonObject = new JSONObject(postData);
            for (String key : jsonObject.keySet()) {
                dataMap.put(key, jsonObject.getString(key));
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
        String jsonResponse = "{\"message\": \"" + errorMsg + "\",\"state\": \"fail\"}";
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        exchange.sendResponseHeaders(400, jsonResponse.getBytes("UTF-8").length);
        try {
            OutputStream os = exchange.getResponseBody();
            os.write(jsonResponse.getBytes());
        } catch (Exception err) {
            err.printStackTrace();
        };
    }
}

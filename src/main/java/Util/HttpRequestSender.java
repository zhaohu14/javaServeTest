package Util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class HttpRequestSender {

    public static String sendPostRequest(String url, String requestBody) throws IOException {
        // 创建 HttpClient
        HttpClient httpClient = HttpClients.createDefault();

        // 创建 POST 请求
        HttpPost httpPost = new HttpPost(url);

        // 设置请求体
        StringEntity stringEntity = new StringEntity(requestBody);
        httpPost.setEntity(stringEntity);

        // 发送请求并获取响应
        HttpResponse response = httpClient.execute(httpPost);

        // 获取响应实体
        HttpEntity entity = response.getEntity();

        // 将响应实体转换为字符串
        String responseBody = EntityUtils.toString(entity);

        // 关闭连接
        httpClient.getConnectionManager().shutdown();

        return responseBody;
    }

    public static String sendGetRequest(String url) throws IOException {
        // 创建 HttpClient
        HttpClient httpClient = HttpClients.createDefault();

        // 创建 GET 请求
        HttpGet httpGet = new HttpGet(url);

        // 发送请求并获取响应
        HttpResponse response = httpClient.execute(httpGet);

        // 获取响应实体
        HttpEntity entity = response.getEntity();

        // 将响应实体转换为字符串
        String responseBody = EntityUtils.toString(entity);

        // 关闭连接
        httpClient.getConnectionManager().shutdown();

        return responseBody;
    }

}

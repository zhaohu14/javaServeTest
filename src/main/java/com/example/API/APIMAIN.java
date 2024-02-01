package com.example.API;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.sql.SQLException;

import static com.example.API.wxLogin.WXLogin;

public class APIMAIN implements HttpHandler{

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String requestMethod = exchange.getRequestMethod();
        String requestPath = exchange.getRequestURI().getPath();
        if (requestPath.equals("/api/wxLogin")) {
            WXLogin(exchange);
        }
//        exchange.close();
    }
}

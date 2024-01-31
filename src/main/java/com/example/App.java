package com.example;
import com.example.API.APIMAIN;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException {
        System.out.println( "Hello World!" );
        HttpServer server = HttpServer.create(new InetSocketAddress(8081), 0);
        server.createContext("/api", new APIMAIN());
        server.setExecutor(null);
        server.start();
        System.out.println("Server started on port " + 8081);
    }
}

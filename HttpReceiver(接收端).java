import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class HttpReceiver {
    public static void main(String[] args) throws IOException {
        int port = 8080;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/receive", new MessageHandler());
        server.start();
        System.out.println("Server is listening on port " + port + " and ready to receive messages.");
    }

    static class MessageHandler implements HttpHandler {
        public void handle(HttpExchange exchange) throws IOException {
            InputStream is = exchange.getRequestBody();
            byte[] buffer = new byte[1024];
            int bytesRead;
            StringBuilder message = new StringBuilder();
            while ((bytesRead = is.read(buffer)) != -1) {
                message.append(new String(buffer, 0, bytesRead, "UTF-8"));
            }
            is.close();

            // 打印接收到的消息到控制台
            System.out.println("Received message: " + message.toString());

            // 发送响应，告知客户端消息已收到
            String response = "Message received successfully";
            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes("UTF-8"));
            os.close();
        }
    }
}

import java.io.OutputStream;//字节输出流
import java.net.HttpURLConnection;//url的http操作
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class HttpSender {

    public static void main(String[] args) {
        String serverUrl = "http://localhost:8080/receive";
        String messageToSend = "damn";
        sendPostRequest(serverUrl, messageToSend);
    }

    private static void sendPostRequest(String urlStr, String message) {
        try {
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "text/plain; charset=UTF-8");
            connection.setDoOutput(true);

            try (OutputStream os = connection.getOutputStream()) {
                os.write(message.getBytes(StandardCharsets.UTF_8));
            }

            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

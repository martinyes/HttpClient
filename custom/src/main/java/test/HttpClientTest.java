package test;

import cloud.lexium.httpclient.HttpMethod;
import cloud.lexium.httpclient.HttpVersion;
import cloud.lexium.httpclient.data.request.HttpRequest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpClientTest {

    public static void main(String[] args) {
        // Custom
        HttpRequest.builder()
                .host("postman-echo.com")
                .path("/get")
                .https()
                .method(HttpMethod.GET)
                .params("key1", "/", "key2", "áépáoépőéápoáé")
                .version(HttpVersion.HTTP_1_1)
                .build()
                .execute()
                .whenComplete((res, ex) -> {
                    System.out.println("Custom: " + res.statusCode());
                });

        // HttpClient
        URL url = null;
        try {
            url = new URL("https://postman-echo.com/get?key1=/&key2=áépáoépőéápoáé");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("GET");
            con.setConnectTimeout(10_000);
            con.setReadTimeout(10_000);

            long start = System.currentTimeMillis();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null)
                content.append(inputLine);
            System.out.println("BEOLVASVA: " + (System.currentTimeMillis() - start) + " ms");

            in.close();
            con.disconnect();

            System.out.println("built-in: " + (System.currentTimeMillis() - start) + " ms");
            System.out.println("HttpClient: " + content.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
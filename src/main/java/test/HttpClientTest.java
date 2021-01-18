package test;

import cloud.lexium.httpclient.HttpClient;
import cloud.lexium.httpclient.HttpMethod;
import cloud.lexium.httpclient.data.request.HttpRequest;

public class HttpClientTest {

    public static void main(String[] args) {
        HttpClient client = new HttpClient();

        // Custom
        HttpRequest.builder()
                // .host("check.torproject.org")
                // .path("/torbulkexitlist")
                .host("postman-echo.com")
                .path("/get")
                .method(HttpMethod.GET)
                .build()
                .sendAsync();

        HttpRequest request = HttpRequest.builder()
                .host("check.torproject.org")
                .path("/torbulkexitlist")
                .https()
                .method(HttpMethod.GET)
                .build();

        try {
            client.send(request);
            client.sendAsync(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
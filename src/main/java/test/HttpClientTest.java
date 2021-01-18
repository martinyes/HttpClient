package test;

import cloud.lexium.httpclient.HttpMethod;
import cloud.lexium.httpclient.data.request.HttpRequest;

public class HttpClientTest {

    public static void main(String[] args) {
        // Custom
        HttpRequest.builder()
                // .host("check.torproject.org")
                // .path("/torbulkexitlist")
                .host("postman-echo.com")
                .path("/get")
                .method(HttpMethod.GET)
                .build()
                .execute();
    }
}
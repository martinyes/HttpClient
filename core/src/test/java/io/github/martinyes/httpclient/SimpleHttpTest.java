package io.github.martinyes.httpclient;

import io.github.martinyes.httpclient.request.HttpRequest;
import io.github.martinyes.httpclient.response.HttpResponse;
import io.github.martinyes.httpclient.scheme.impl.SocketScheme;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class SimpleHttpTest {

    private final CountDownLatch LOCK = new CountDownLatch(1);

    private static final HttpContainer POSTMAN_CLIENT = HttpContainer.newContainer()
            .build();

    @Test
    @DisplayName("An HTTP Request to test a simple GET request.")
    void anHttpRequestToTestASimpleGetRequest() throws InterruptedException, URISyntaxException {
        HttpRequest request = HttpRequest.builder()
                .uri(new URI("https://postman-echo.com/get"))
                //.scheme(new SocketScheme())
                //.version(HttpVersion.HTTP_1_1)
                .method(HttpMethod.GET)
                .build();

        CompletableFuture<HttpResponse<String>> res;
        try {
            res = POSTMAN_CLIENT.sendAsync(request, HttpResponse.BodyHandlers.asString());
            res.whenComplete((r, ex) -> {
                System.out.println(r.body());
                System.out.println(r.protocol());
                r.headers().print();
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        LOCK.await(2000, TimeUnit.MILLISECONDS);
    }

    @Test
    @DisplayName("An HTTP Request to test query parameters.")
    void anHttpRequestToTestQueryParameters() throws InterruptedException, URISyntaxException {
        HttpRequest request = HttpRequest.builder()
                .uri(new URI("https://postman-echo.com/get"))
                .method(HttpMethod.GET)
                .version(HttpVersion.HTTP_2)
                .param("key1", "value1")
                .param("key1", "value2")
                .params("key1", "value3", "key2", "value2")
                .build();

        CompletableFuture<HttpResponse<String>> res;
        try {
            res = POSTMAN_CLIENT.sendAsync(request, HttpResponse.BodyHandlers.asString());
            res.whenComplete((r, ex) -> {
                System.out.println(r.body());
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        LOCK.await(2000, TimeUnit.MILLISECONDS);
    }

    @Test
    @DisplayName("An HTTP Request to test headers manipulation.")
    void anHttpRequestToTestHeadersManipulation() throws URISyntaxException {
        HttpRequest request = HttpRequest.builder()
                .uri(new URI("https://postman-echo.com/get"))
                .method(HttpMethod.GET)
                .header("test", "value value")
                .header("test", "value2")
                .header("test2", "value1")
                .headers("test2", "value3")
                .build();

        HttpResponse<String> res;
        try {
            res = POSTMAN_CLIENT.send(request, HttpResponse.BodyHandlers.asString());
            System.out.println(res.body());
            System.out.println(res.headers().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("An HTTP Request to test body manipulation.")
    void anHttpRequestToTestBodyManipulation() throws URISyntaxException {
       HttpRequest request = HttpRequest.builder()
                .uri(new URI("https://postman-echo.com/post"))
                .method(HttpMethod.POST)
                .build();

       /* HttpClient client = HttpClient.newHttpClient();
        java.net.http.HttpRequest request = java.net.http.HttpRequest.newBuilder()
                .uri(URI.create("https://postman-echo.com/post"))
                .POST(java.net.http.HttpRequest.BodyPublishers.ofString("{\"action\":\"hello\"}"))
                .build();

        CompletableFuture<java.net.http.HttpResponse<String>> response = client.sendAsync(request, java.net.http.HttpResponse.BodyHandlers.ofString());
        response.whenComplete((res, ex) -> System.out.println(res.body()));

        try {
            LOCK.await(2000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }*/
    }
}
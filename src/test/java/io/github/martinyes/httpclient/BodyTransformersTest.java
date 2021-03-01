package io.github.martinyes.httpclient;

import io.github.martinyes.httpclient.data.request.HttpRequest;
import io.github.martinyes.httpclient.data.response.HttpResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class BodyTransformersTest {

    private final CountDownLatch LOCK = new CountDownLatch(1);

    private static final HttpClient POSTMAN_CLIENT = HttpClient.newClient()
            .host("postman-echo.com")
            .https()
            .build();

    @Test
    @DisplayName("An HTTP Request to test String body type.")
    void anHttpRequestToTestStringBodyType() throws InterruptedException {
        HttpRequest request = HttpRequest.builder()
                .path("/get")
                .method(HttpMethod.GET)
                .build();

        CompletableFuture<HttpResponse<String>> res;
        try {
            res = POSTMAN_CLIENT.sendAsync(request, HttpResponse.BodyHandlers.asString());

            res.whenComplete((r, ex) -> System.out.println(r.body()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        LOCK.await(2000, TimeUnit.MILLISECONDS);
    }

    @Test
    @DisplayName("An HTTP Request to test byte array body type.")
    void anHttpRequestToTestByteArrayBodyType() throws InterruptedException {
        HttpRequest request = HttpRequest.builder()
                .path("/get")
                .method(HttpMethod.GET)
                .build();

        CompletableFuture<HttpResponse<byte[]>> res;
        try {
            res = POSTMAN_CLIENT.sendAsync(request, HttpResponse.BodyHandlers.asBytes());
            res.whenComplete((r, ex) -> System.out.println(new String(r.body())));
        } catch (IOException e) {
            e.printStackTrace();
        }

        LOCK.await(2000, TimeUnit.MILLISECONDS);
    }
}
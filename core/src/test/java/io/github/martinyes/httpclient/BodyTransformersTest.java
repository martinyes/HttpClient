package io.github.martinyes.httpclient;

import com.google.gson.JsonObject;
import io.github.martinyes.httpclient.request.HttpRequest;
import io.github.martinyes.httpclient.response.HttpResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class BodyTransformersTest {

    private final CountDownLatch LOCK = new CountDownLatch(1);

    private static final HttpContainer POSTMAN_CLIENT = HttpContainer.newContainer()
            .build();

    @Test
    @DisplayName("An HTTP Request to test String body type.")
    void anHttpRequestToTestStringBodyType() throws InterruptedException, URISyntaxException {
        HttpRequest request = HttpRequest.builder()
                .uri(new URI("https://postman-echo.com/get"))
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
    void anHttpRequestToTestByteArrayBodyType() throws InterruptedException, URISyntaxException {
        HttpRequest request = HttpRequest.builder()
                .uri(new URI("https://postman-echo.com/get"))
                .method(HttpMethod.GET)
                .build();

        CompletableFuture<HttpResponse<byte[]>> res;
        try {
            res = POSTMAN_CLIENT.sendAsync(request, HttpResponse.BodyHandlers.asBytes());
            res.whenComplete((r, ex) -> System.out.println(Arrays.toString(r.body())));
        } catch (IOException e) {
            e.printStackTrace();
        }

        LOCK.await(2000, TimeUnit.MILLISECONDS);
    }

    @Test
    @DisplayName("An HTTP Request to test json body type.")
    void anHttpRequestToTestJsonBodyType() throws InterruptedException, URISyntaxException {
        HttpRequest request = HttpRequest.builder()
                .uri(new URI("https://postman-echo.com/get"))
                .method(HttpMethod.GET)
                .build();

        CompletableFuture<HttpResponse<JsonObject>> res;
        try {
            res = POSTMAN_CLIENT.sendAsync(request, HttpResponse.BodyHandlers.asJson());
            res.whenComplete((r, ex) -> System.out.println(r.body().get("url")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        LOCK.await(2000, TimeUnit.MILLISECONDS);
    }
}
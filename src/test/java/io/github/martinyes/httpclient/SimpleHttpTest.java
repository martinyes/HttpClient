package io.github.martinyes.httpclient;

import io.github.martinyes.httpclient.data.request.HttpRequest;
import io.github.martinyes.httpclient.data.response.HttpResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class SimpleHttpTest {

    private CountDownLatch lock = new CountDownLatch(1);

    @Test
    @DisplayName("Simple JUnit test to test a GET request")
    void simpleJUnitTestToTestAGetRequest() throws InterruptedException {
        CompletableFuture<HttpResponse> future = HttpRequest.builder()
                .host("postman-echo.com")
                .path("/get")
                .https()
                .method(HttpMethod.GET)
                .build()
                .sendAsync();

        future.whenComplete((res, ex) -> {
            System.out.println(res.body());
        });

        lock.await(2000, TimeUnit.MILLISECONDS);
    }
}
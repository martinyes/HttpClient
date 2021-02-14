package io.github.martinyes.httpclient;

import io.github.martinyes.httpclient.data.request.HttpRequest;
import io.github.martinyes.httpclient.data.response.HttpResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class SimpleHttpTest {

    private final CountDownLatch LOCK = new CountDownLatch(1);

    private static final HttpClient POSTMAN_CLIENT = HttpClient.newClient()
            .host("postman-echo.com")
            .https()
            .build();

    @Test
    @DisplayName("Simple JUnit test to test a GET request")
    void simpleJUnitTestToTestAGetRequest() throws Exception {
        HttpRequest request = HttpRequest.builder()
                .path("/get")
                .method(HttpMethod.GET)
                .build();
        CompletableFuture<HttpResponse> res = POSTMAN_CLIENT.sendAsync(request);

        res.whenComplete((r, ex) -> {
            System.out.println(r.body());
        });

        LOCK.await(2000, TimeUnit.MILLISECONDS);
    }
}
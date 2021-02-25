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
        CompletableFuture<HttpResponse<String>> res = POSTMAN_CLIENT.sendAsync(request, HttpResponse.BodyHandlers.asString());

        res.whenComplete((r, ex) -> {
            System.out.println(r.body());
        });

        LOCK.await(2000, TimeUnit.MILLISECONDS);
    }

    @Test
    @DisplayName("JUnit Test to test query parameters in GET requests")
    void junitTestToTestQueryParametersInGetRequests() throws Exception {
        HttpRequest request = HttpRequest.builder()
                .path("/get")
                .method(HttpMethod.GET)
                .param("key1", "value1")
                .param("key1", "value2")
                .params("key1", "value3", "key2", "value2")
                .build();
        CompletableFuture<HttpResponse<String>> res = POSTMAN_CLIENT.sendAsync(request, HttpResponse.BodyHandlers.asString());

        res.whenComplete((r, ex) -> {
            System.out.println(r.body());
        });

        LOCK.await(2000, TimeUnit.MILLISECONDS);
    }

    @Test
    @DisplayName("JUnit Test to test adding additional headers to the request")
    void jUnitTestToTestAddingAdditionalHeadersToTheRequest() throws Exception {
        HttpRequest request = HttpRequest.builder()
                .path("/get")
                .method(HttpMethod.GET)
                .header("test", "value value")
                .header("test", "value2")
                .header("test2", "value1")
                .headers("test2", "value3")
                .build();
        HttpResponse<String> res = POSTMAN_CLIENT.send(request, HttpResponse.BodyHandlers.asString());

        System.out.println(res.body());
    }
}
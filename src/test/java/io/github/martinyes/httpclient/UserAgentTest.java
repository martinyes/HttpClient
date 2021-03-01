package io.github.martinyes.httpclient;

import io.github.martinyes.httpclient.data.request.HttpRequest;
import io.github.martinyes.httpclient.data.response.HttpResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class UserAgentTest {

    private final CountDownLatch LOCK = new CountDownLatch(1);

    private static final HttpClient CUSTOM = HttpClient.newClient()
            .userAgent("CUSTOM/1.0")
            .host("postman-echo.com")
            .https()
            .build();

    private static final HttpClient NON_CUSTOM = HttpClient.newClient()
            .host("postman-echo.com")
            .https()
            .build();

    @Test
    @DisplayName("An HTTP Request with the default user agent of HTTP Client.")
    void aHttpRequestWithTheDefaultUserAgentOfHttpClient() throws InterruptedException {
        HttpRequest request = HttpRequest.builder()
                .path("/get")
                .method(HttpMethod.GET)
                .build();

        AtomicReference<String> userAgent = new AtomicReference<>();
        try {
            NON_CUSTOM.sendAsync(request, HttpResponse.BodyHandlers.asString()).whenComplete((res, e) -> {
                userAgent.set(res.request().getUserAgent());
                System.out.println(res.request().getUserAgent());
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        LOCK.await(1000, TimeUnit.MILLISECONDS);

        Assertions.assertEquals(NON_CUSTOM.getUserAgent(), userAgent.get());
    }

    @Test
    @DisplayName("An HTTP Request with custom user agent in HTTP Client.")
    void anHttpRequestWithCustomUserAgentInHttpClient() throws InterruptedException {
        HttpRequest request = HttpRequest.builder()
                .path("/get")
                .method(HttpMethod.GET)
                .build();

        AtomicReference<String> userAgent = new AtomicReference<>();
        try {
            CUSTOM.sendAsync(request, HttpResponse.BodyHandlers.asString()).whenComplete((res, e) -> {
                userAgent.set(res.request().getUserAgent());
                System.out.println(res.request().getUserAgent());
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        LOCK.await(2000, TimeUnit.MILLISECONDS);

        Assertions.assertEquals(CUSTOM.getUserAgent(),
                userAgent.get());
    }

    @Test
    @DisplayName("An HTTP Request with custom user agent in HTTP Request.")
    void anHttpRequestWithCustomUserAgentInHttpRequest() throws InterruptedException {
        HttpRequest request = HttpRequest.builder()
                .path("/get")
                .userAgent("Mozilla/1.22 (compatible; MSIE 10.0; Windows 3.1")
                .method(HttpMethod.GET)
                .build();

        AtomicReference<String> userAgent = new AtomicReference<>();
        try {
            CUSTOM.sendAsync(request, HttpResponse.BodyHandlers.asString()).whenComplete((res, e) -> {
                userAgent.set(res.request().getUserAgent());
                System.out.println(res.request().getUserAgent());
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        LOCK.await(2000, TimeUnit.MILLISECONDS);

        Assertions.assertNotEquals(CUSTOM.getUserAgent(),
                userAgent.get());

        Assertions.assertEquals("Mozilla/1.22 (compatible; MSIE 10.0; Windows 3.1",
                userAgent.get());
    }

    @Test
    @DisplayName("An HTTP request with both client and request user-agent defined.")
    void anHttpRequestWithBothClientAndRequestUserAgentDefined() throws InterruptedException {
        HttpRequest request = HttpRequest.builder()
                .path("/get")
                .userAgent("Mozilla/1.22 (compatible; MSIE 10.0; Windows 3.1")
                .method(HttpMethod.GET)
                .build();

        AtomicReference<String> userAgent = new AtomicReference<>();
        try {
            CUSTOM.sendAsync(request, HttpResponse.BodyHandlers.asString()).whenComplete((res, e) -> {
                userAgent.set(res.request().getUserAgent());
                System.out.println(res.request().getUserAgent());
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        LOCK.await(2000, TimeUnit.MILLISECONDS);

        Assertions.assertNotEquals(CUSTOM.getUserAgent(),
                userAgent.get());

        Assertions.assertEquals("Mozilla/1.22 (compatible; MSIE 10.0; Windows 3.1",
                userAgent.get());
    }
}
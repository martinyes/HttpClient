package cloud.lexium.httpclient;

import cloud.lexium.httpclient.data.request.HttpRequest;
import cloud.lexium.httpclient.data.response.HttpResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

public class SimpleHttpTest {

    @Test
    @DisplayName("Simple JUnit test to test a GET request")
    void simpleJUnitTestToTestAGetRequest() {
        CompletableFuture<HttpResponse> future = HttpRequest.builder()
                .host("postman-echo.com")
                .path("/get")
                .https()
                .method(HttpMethod.GET)
                .build()
                .sendAsync();

        future.whenComplete((res, ex) -> {
            System.out.println(res.protocol());
        });
    }
}
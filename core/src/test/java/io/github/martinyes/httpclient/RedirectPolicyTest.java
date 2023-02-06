package io.github.martinyes.httpclient;

import io.github.martinyes.httpclient.request.HttpRequest;
import io.github.martinyes.httpclient.response.HttpResponse;
import io.github.martinyes.httpclient.scheme.impl.SocketScheme;
import io.github.martinyes.httpclient.scheme.impl.UrlConnectionScheme;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class RedirectPolicyTest {

    private static final HttpContainer SERVER = HttpContainer.newContainer()
            .build();

    private final CountDownLatch LOCK = new CountDownLatch(1);

    @Test
    @DisplayName("An HTTP Request to test the redirect policy")
    void anHttpRequestToTestTheRedirectPolicy() throws Exception {
        HttpRequest request = HttpRequest.builder()
                .uri(new URI("http://127.0.0.1:5284/redirect"))
                .method(HttpMethod.GET)
                .followRedirects()
                .build();

        HttpResponse<String> res = SERVER.send(request, HttpResponse.BodyHandlers.asString());

        System.out.println(res.body());
        System.out.println(res.statusCode());
        LOCK.await(2000, TimeUnit.MILLISECONDS);
    }

    @Test
    @DisplayName("An HTTP Request to test the redirect loop handling")
    void anHttpRequestToTestTheRedirectLoopHandling() throws Exception {
        HttpRequest request = HttpRequest.builder()
                .uri(new URI("http://127.0.0.1:5284/redirectLoop"))
                .method(HttpMethod.GET)
                .followRedirects()
                .build();

        HttpResponse<String> res = SERVER.send(request, HttpResponse.BodyHandlers.asString());

        System.out.println(res.body());
        LOCK.await(2000, TimeUnit.MILLISECONDS);
    }
}
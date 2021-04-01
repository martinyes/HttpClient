package io.github.martinyes.httpclient;

import io.github.martinyes.httpclient.data.request.HttpRequest;
import io.github.martinyes.httpclient.data.response.HttpResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class RedirectPolicyTest {

    private static final HttpClient SERVER = HttpClient.newClient()
            .host("127.0.0.1")
            .port(7000)
            .build();

    @Test
    @DisplayName("An HTTP Request to test the redirect policy")
    void anHttpRequestToTestTheRedirectPolicy() throws IOException {
        HttpRequest request = HttpRequest.builder()
                .path("/redirect")
                .method(HttpMethod.GET)
                .build();

        HttpResponse<String> res = SERVER.send(request, HttpResponse.BodyHandlers.asString());

        System.out.println(res.body());
    }
}
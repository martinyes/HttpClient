package cloud.lexium.httpclient;

import cloud.lexium.httpclient.data.HttpMethod;
import cloud.lexium.httpclient.data.response.HttpResponse;
import lombok.Builder;
import lombok.Getter;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;

@Builder
@Getter
public class HttpRequest {

    private final String host;
    @Builder.Default private final int port = 80;
    private final boolean https;
    private final HttpMethod method;
    private final String path;
    private final Duration timeout;

    public CompletableFuture<HttpResponse> execute() {
        try {
            return CompletableFuture.completedFuture(new HttpClient().sendRequest(this));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static class HttpRequestBuilder {
        public HttpRequestBuilder https() {
            this.https = true;
            return this;
        }
    }
}
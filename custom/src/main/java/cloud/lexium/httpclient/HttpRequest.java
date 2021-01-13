package cloud.lexium.httpclient;

import cloud.lexium.httpclient.config.IConfig;
import cloud.lexium.httpclient.config.impl.DefaultConfiguration;
import cloud.lexium.httpclient.data.HttpMethod;
import cloud.lexium.httpclient.data.HttpVersion;
import cloud.lexium.httpclient.data.response.HttpResponse;
import lombok.Builder;
import lombok.Getter;

import java.util.concurrent.CompletableFuture;

@Builder
@Getter
public class HttpRequest {

    private final String host;
    @Builder.Default private final int port = -1;
    @Builder.Default private final IConfig config = new DefaultConfiguration();
    @Builder.Default private final HttpVersion version = HttpVersion.HTTP_1;
    private final boolean https;
    private final HttpMethod method;
    @Builder.Default private final String path = "/";

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
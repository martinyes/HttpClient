package cloud.lexium.httpclient.data.request;

import cloud.lexium.httpclient.HttpClient;
import cloud.lexium.httpclient.HttpMethod;
import cloud.lexium.httpclient.HttpVersion;
import cloud.lexium.httpclient.config.Config;
import cloud.lexium.httpclient.config.impl.DefaultConfig;
import cloud.lexium.httpclient.data.response.HttpResponse;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import lombok.Builder;
import lombok.Getter;

import java.util.concurrent.CompletableFuture;

@Builder
@Getter
public class HttpRequest {

    private final String host;
    @Builder.Default private final int port = 80;
    @Builder.Default private final String path = "/";
    private final HttpMethod method;

    @Builder.Default private final Config config = new DefaultConfig();
    @Builder.Default private final HttpVersion version = HttpVersion.HTTP_1;
    private final boolean https;

    private final Multimap<String, String> params;

    public CompletableFuture<HttpResponse> execute() {
        try {
            return CompletableFuture.completedFuture(new HttpClient().send(this));
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

        public HttpRequestBuilder params(String... params) {
            if (this.params == null)
                this.params = ArrayListMultimap.create();

            this.params.putAll(ParamProcessor.parse(params));
            return this;
        }
    }
}
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

/**
 * This class represents an HTTP Request with all its configuration options, like: protocol version, method, etc.
 * <p>
 * These requests sent through an HTTP Client {@link HttpClient}, that can be created manually
 * or automatically by the HTTP Request Builder.
 *
 * @author martin
 */
@Builder
@Getter
public class HttpRequest {

    /**
     * Remote server options
     */
    private final String host;
    @Builder.Default private final int port = 80;
    private final boolean https;

    /**
     * Request options
     */
    @Builder.Default private final String path = "/";
    private final HttpMethod method;
    @Builder.Default private final HttpVersion version = HttpVersion.HTTP_1;
    @Builder.Default private final Config config = new DefaultConfig();

    /**
     * Message options
     */
    private final Multimap<String, String> params;

    /**
     * Sends the given request synchronously using an HTTP Client {@link HttpClient}.
     * It blocks the thread until the request has been send and a response has been received.
     *
     * @return an HTTP Response
     */
    public HttpResponse send() {
        return null;
    }

    /**
     * Sends the given request asynchronously using an HTTP Client {@link HttpClient}.
     * This method returns immediately with a CompletableFuture<HttpResponse>.
     *
     * @return a {@link CompletableFuture<HttpResponse>}
     */
    public CompletableFuture<HttpResponse> sendAsync() {
        try {
            return CompletableFuture.completedFuture(new HttpClient().sendAsync(this));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * HTTP Request Builder Class.
     *
     * @author martin
     */
    public static class HttpRequestBuilder {
        public HttpRequestBuilder https() {
            this.https = true;
            return this;
        }

        public HttpRequestBuilder params(String... params) {
            if (this.params == null)
                this.params = ArrayListMultimap.create();

            this.params.putAll(ParamProcessor.parseParams(params));
            return this;
        }
    }
}
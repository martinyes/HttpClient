package io.github.martinyes.httpclient.data.request;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.github.martinyes.httpclient.*;
import io.github.martinyes.httpclient.net.ClientHandler;
import io.github.martinyes.httpclient.net.impl.SocketClient;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This class represents an HTTP Request with the support for changing its values like:
 * <ul>
 *  <li>Version</li>
 *  <li>Method</li>
 *  <li>Executor</li>
 *  <li>Timeouts</li>
 * </ul>
 * <p>
 * These requests sent through an {@link HttpClient}, that can be created manually
 * through a HTTP Client Builder.
 *
 * @author martin
 * @since 1
 */
@Builder
@Getter
public class HttpRequest {

    /**
     * Execution options
     */
    @Builder.Default private final ClientHandler handler = new SocketClient();
    @Builder.Default private final ExecutorService executor = Executors.newCachedThreadPool(new ThreadFactoryBuilder().setDaemon(false).build());
    @Setter private String userAgent;

    /**
     * Basic options
     */
    @Builder.Default private final String path = "/";
    private final HttpMethod method;
    @Builder.Default private final boolean disableRedirects = false;
    @Builder.Default private final HttpVersion version = HttpVersion.HTTP_1;

    private final HttpHeaders headers;
    private final HttpParams params;

    /**
     * HTTP Request Builder Class.
     *
     * @author martin
     * @since 2
     */
    public static class HttpRequestBuilder {
        public HttpRequestBuilder() {
            this.headers = new HttpHeaders();
            this.params = new HttpParams();
        }

        public HttpRequestBuilder disableRedirects() {
            this.disableRedirects$set = true;
            return this;
        }

        public HttpRequestBuilder headers(String... headers) {
            this.headers.parse(headers);
            return this;
        }

        public HttpRequestBuilder header(String key, String value) {
            this.headers.parse(new String[]{key, value});
            return this;
        }

        public HttpRequestBuilder params(String... params) {
            this.params.parse(params);
            return this;
        }

        public HttpRequestBuilder param(String key, String value) {
            this.params.parse(new String[]{key, value});
            return this;
        }
    }
}
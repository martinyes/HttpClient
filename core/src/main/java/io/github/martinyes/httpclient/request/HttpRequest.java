package io.github.martinyes.httpclient.request;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.github.martinyes.httpclient.HttpContainer;
import io.github.martinyes.httpclient.HttpMethod;
import io.github.martinyes.httpclient.HttpParams;
import io.github.martinyes.httpclient.HttpVersion;
import io.github.martinyes.httpclient.utils.HttpHeaderParser;
import io.github.martinyes.httpclient.scheme.Scheme;
import io.github.martinyes.httpclient.scheme.impl.SocketScheme;
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
 * These requests sent through an {@link HttpContainer}, that can be created manually
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
    @Builder.Default private final Scheme handler = new SocketScheme();
    @Builder.Default private final ExecutorService executor = Executors.newCachedThreadPool(new ThreadFactoryBuilder().setDaemon(false).build());
    @Setter private String userAgent;

    /**
     * Basic options
     */
    @Builder.Default private final String path = "/";
    private final HttpMethod method;
    @Builder.Default private final boolean disableRedirects = false;
    @Builder.Default private final HttpVersion version = HttpVersion.HTTP_1;

    private final HttpHeaderParser headers;
    private final HttpParams params;

    /**
     * HTTP Request Builder Class.
     *
     * @author martin
     * @since 2
     */
    public static class HttpRequestBuilder {
        public HttpRequestBuilder() {
            this.headers = new HttpHeaderParser();
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

        public HttpRequestBuilder body() {
            return this;
        }
    }
}
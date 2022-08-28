package io.github.martinyes.httpclient.request;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.github.martinyes.httpclient.HttpContainer;
import io.github.martinyes.httpclient.HttpMethod;
import io.github.martinyes.httpclient.HttpParams;
import io.github.martinyes.httpclient.HttpVersion;
import io.github.martinyes.httpclient.scheme.Scheme;
import io.github.martinyes.httpclient.scheme.impl.UrlConnectionScheme;
import io.github.martinyes.httpclient.utils.HttpHeaderParser;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.net.URI;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Predicate;

/**
 * A class representing an HTTP Request.
 * An HTTP Request is sent through an {@link HttpContainer}, that can be created manually through an HTTP Container Builder.
 *
 * @author martin
 * @since 1
 * @version 2
 */
@Builder
@Getter
public class HttpRequest {

    /**
     * Execution options
     */
    @Builder.Default private final Scheme scheme = new UrlConnectionScheme();
    @Builder.Default private final ExecutorService executor = Executors.newCachedThreadPool(new ThreadFactoryBuilder().setDaemon(false).build());
    @Setter private String userAgent;

    /**
     * Basic options
     */
    private final URI uri;
    private final HttpMethod method;
    @Builder.Default private final HttpVersion version = HttpVersion.HTTP_1;

    private final HttpHeaderParser headers;
    private final HttpParams params;
    private final boolean followRedirects;

    /**
     * Non-adjustable options
     */
    @Setter private int redirectsCompleted;
    @Getter public static final Predicate<Integer> REDIRECTS_RULE = i -> (i < 5);


    /**
     * HTTP Request Builder Class.
     * <p>
     * This class is used to create an HTTP Request.
     *
     * @author martin
     * @since 1
     */
    public static class HttpRequestBuilder {
        public HttpRequestBuilder() {
            this.headers = new HttpHeaderParser();
            this.params = new HttpParams();
            this.followRedirects = false;
        }

        public HttpRequestBuilder followRedirects() {
            this.followRedirects = true;
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
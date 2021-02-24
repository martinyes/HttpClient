package io.github.martinyes.httpclient.data.request;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.github.martinyes.httpclient.HttpClient;
import io.github.martinyes.httpclient.HttpMethod;
import io.github.martinyes.httpclient.HttpVersion;
import io.github.martinyes.httpclient.net.ClientHandler;
import io.github.martinyes.httpclient.net.impl.SocketClient;
import lombok.Builder;
import lombok.Getter;

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
    @Builder.Default private final ExecutorService executor = Executors.newCachedThreadPool(new ThreadFactoryBuilder().setDaemon(false).build());
    @Builder.Default private final ClientHandler handler = new SocketClient();

    /**
     * Basic options
     */
    @Builder.Default private final String path = "/";
    private final HttpMethod method;
    @Builder.Default private final boolean disableRedirects = false;
    @Builder.Default private final HttpVersion version = HttpVersion.HTTP_1;

    private final Multimap<String, String> params;

    /**
     * HTTP Request Builder Class.
     *
     * @author martin
     * @since 2
     */
    public static class HttpRequestBuilder {
        public HttpRequestBuilder() {
            this.params = ArrayListMultimap.create();
        }

        public HttpRequestBuilder disableRedirects() {
            this.disableRedirects$set = true;
            return this;
        }

        public HttpRequestBuilder headers(String... headers) {
            return this;
        }

        public HttpRequestBuilder header(String key, String value) {
            return this;
        }

        public HttpRequestBuilder params(String... params) {
            this.params.putAll(ParamProcessor.parseParams(params));
            return this;
        }

        public HttpRequestBuilder param(String key, String value) {
            this.params(key, value);
            return this;
        }
    }
}
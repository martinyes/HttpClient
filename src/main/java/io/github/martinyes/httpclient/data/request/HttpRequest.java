package io.github.martinyes.httpclient.data.request;

import io.github.martinyes.httpclient.HttpClient;
import io.github.martinyes.httpclient.HttpMethod;
import io.github.martinyes.httpclient.HttpVersion;
import io.github.martinyes.httpclient.config.Config;
import io.github.martinyes.httpclient.config.impl.DefaultConfig;
import io.github.martinyes.httpclient.data.response.HttpResponse;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.Builder;
import lombok.Getter;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
     * Basic options
     */
    @Builder.Default private final ExecutorService executor = Executors.newCachedThreadPool(new ThreadFactoryBuilder().setDaemon(false).build());

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
    @Builder.Default private final boolean disableRedirects = true;
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
            return new HttpClient().sendAsync(this);
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

        public HttpRequestBuilder disableRedirects() {
            this.disableRedirects$set = true;
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
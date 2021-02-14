package io.github.martinyes.httpclient;

import io.github.martinyes.httpclient.config.Config;
import io.github.martinyes.httpclient.config.impl.DefaultConfig;
import io.github.martinyes.httpclient.data.request.HttpRequest;
import io.github.martinyes.httpclient.data.response.HttpResponse;
import io.github.martinyes.httpclient.data.response.scheme.impl.DefaultScheme;
import io.github.martinyes.httpclient.net.ClientHandler;
import lombok.Builder;
import lombok.Getter;

import java.io.IOException;
import java.net.InetAddress;
import java.util.concurrent.CompletableFuture;

/**
 * HTTP Client.
 * <p>
 * This class acts like a container. Used to send one or multiple requests and parse their responses.
 * An HTTP client is created manually through a client builder.
 * The builder can be used to configure the request itself, like: host, port, config handler.
 * <p>
 * Requests can be sent in two ways: synchronously (blocking) or asynchronously (non-blocking).
 * <p>
 * {@link HttpClient#send(HttpRequest)} blocks until the request has been sent and the response has been received.
 * {@link HttpClient#sendAsync(HttpRequest)} sends the request and receives the response asynchronously.
 * It returns immediately with a {@link CompletableFuture<HttpResponse>}
 *
 * @author martin
 */
@Getter
@Builder(builderMethodName = "newClient")
public class HttpClient {

    /**
     * Basic options
     */
    @Builder.Default private final Config config = new DefaultConfig();

    /**
     * Remote server options
     */
    @Builder.Default private final int port = 80;
    private final String host;
    private final boolean https;

    /**
     * This method used to send the given request synchronously.
     * It blocks the thread until the request has been send and a response has been received.
     *
     * @param request the configured request
     * @return a HTTP Response
     */
    public HttpResponse send(HttpRequest request) throws IOException {
        ClientHandler client = request.getHandler();

        client.connect(InetAddress.getByName(host), port, https);
        client.send(this, request);

        return parseResponse(client.read(), request);
    }

    /**
     * This method used to send the given request asynchronously.
     * It returns immediately with a CompletableFuture of {@link HttpResponse}.
     *
     * @param request the configured request
     * @return a CompletableFuture<HttpResponse>
     * @throws IOException
     */
    public CompletableFuture<HttpResponse> sendAsync(HttpRequest request) throws IOException {
        ClientHandler client = request.getHandler();
        client.connect(InetAddress.getByName(host), port, https);

        CompletableFuture<HttpResponse> future = new CompletableFuture<>();

        request.getExecutor().submit(() -> {
            try {
                client.send(this, request);

                HttpResponse res = parseResponse(client.read(), request);
                future.complete(res);
                client.disconnect();
            } catch (Throwable t) {
                future.completeExceptionally(t);
            }
        });

        request.getExecutor().shutdown();

        return future;
    }

    private HttpResponse parseResponse(String data, HttpRequest request) {
        if (request.getVersion() == HttpVersion.HTTP_2) {
            return null;
        }

        return new DefaultScheme().parseResponse(request, data);
    }

    /**
     * HTTP Client Builder Class.
     *
     * @author martin
     */
    public static class HttpClientBuilder {
        public HttpClientBuilder https() {
            this.https = true;
            return this;
        }
    }
}
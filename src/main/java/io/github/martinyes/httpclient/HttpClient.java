package io.github.martinyes.httpclient;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.github.martinyes.httpclient.config.Config;
import io.github.martinyes.httpclient.config.impl.DefaultConfig;
import io.github.martinyes.httpclient.data.request.HttpRequest;
import io.github.martinyes.httpclient.data.response.BodyHandler;
import io.github.martinyes.httpclient.data.response.HttpResponse;
import io.github.martinyes.httpclient.data.response.impl.WrappedHttpResponse;
import io.github.martinyes.httpclient.data.response.scheme.impl.DefaultScheme;
import io.github.martinyes.httpclient.net.ClientHandler;
import lombok.Builder;
import lombok.Getter;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * HTTP Client.
 * <p>
 * This class acts like a container. Used to send one or multiple requests and parse their responses.
 * An HTTP client is created manually through a client builder.
 * The builder can be used to configure the request itself, like: host, port, config handler.
 * <p>
 * Requests can be sent in two ways: synchronously (blocking) or asynchronously (non-blocking).
 * <p>
 * {@link HttpClient#send(HttpRequest, BodyHandler)} blocks until the request has been sent and the response has been received.
 * {@link HttpClient#sendAsync(HttpRequest, BodyHandler)} sends the request and receives the response asynchronously.
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
    @Builder.Default private final ExecutorService executor = Executors.newCachedThreadPool(new ThreadFactoryBuilder().setDaemon(false).build());
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
     * @return an HTTP Response
     * @throws IOException if an I/O error occurs when sending or receiving
     */
    public <T> HttpResponse<T> send(HttpRequest request, BodyHandler<T> bodyHandler) throws IOException {
        ClientHandler client = request.getHandler();

        client.connect(InetAddress.getByName(host), port, https);
        client.send(this, request);

        return parseResponse(client.read(), request, bodyHandler);
    }

    /**
     * This method used to send the given request asynchronously.
     * It returns immediately with a CompletableFuture of {@link HttpResponse}.
     *
     * @param request the configured request
     * @return a CompletableFuture<HttpResponse>
     * @throws IOException if an I/O error occurs when sending or receiving
     */
    public <T> CompletableFuture<HttpResponse<T>> sendAsync(HttpRequest request, BodyHandler<T> bodyHandler) throws IOException {
        ClientHandler client = request.getHandler();
        client.connect(InetAddress.getByName(host), port, https);

        CompletableFuture<HttpResponse<T>> future = new CompletableFuture<>();
        ExecutorService service = (request.getExecutor() == null ? this.executor : request.getExecutor());

        service.submit(() -> {
            try {
                client.send(this, request);

                HttpResponse<T> res = parseResponse(client.read(), request, bodyHandler);
                future.complete(res);
                client.disconnect();
            } catch (Throwable t) {
                future.completeExceptionally(t);
            }
        });

        service.shutdown();

        return future;
    }

    private <T> HttpResponse<T> parseResponse(String data, HttpRequest request, BodyHandler<T> bodyHandler) {
        if (request.getVersion() == HttpVersion.HTTP_2) {
            throw new RuntimeException("HTTP/2 protocol is not supported yet.");
        }

        WrappedHttpResponse wrapped = new DefaultScheme().parseResponse(request, data);

        return new HttpResponse<T>() {
            @Override
            public HttpRequest request() {
                return request;
            }

            @Override
            public int statusCode() {
                return wrapped.getStatus().getCode();
            }

            @Override
            public String protocol() {
                return wrapped.getStatus().getProtocol();
            }

            @Override
            public String statusText() {
                return wrapped.getStatus().getText();
            }

            @Override
            public Map<String, String> headers() {
                return wrapped.getHeaders();
            }

            @Override
            public T body() {
                return bodyHandler.apply(wrapped);
            }
        };
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
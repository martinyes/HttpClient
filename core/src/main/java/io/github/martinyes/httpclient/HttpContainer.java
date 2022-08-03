package io.github.martinyes.httpclient;

import io.github.martinyes.httpclient.request.HttpRequest;
import io.github.martinyes.httpclient.response.body.BodyType;
import io.github.martinyes.httpclient.response.HttpResponse;
import io.github.martinyes.httpclient.response.WrappedHttpResponse;
import io.github.martinyes.httpclient.response.scheme.impl.DefaultScheme;
import io.github.martinyes.httpclient.scheme.Scheme;
import lombok.Builder;
import lombok.Getter;

import java.io.IOException;
import java.net.InetAddress;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

/**
 * HTTP Container.
 * <p>
 * This class acts like a container. Used to send one or multiple requests and parse their responses.
 * An HTTP Container is created manually through a client builder.
 * The builder can be used to configure the request itself, like: host, port, config handler.
 * <p>
 * Requests can be sent in two ways: synchronously (blocking) or asynchronously (non-blocking).
 * <ul>
 *     <li>{@link HttpContainer#send(HttpRequest, BodyType)} blocks until the request has been sent and the response has been received.</li>
 *     <li>{@link HttpContainer#sendAsync(HttpRequest, BodyType)} sends the request and receives the response asynchronously. It returns immediately with a {@link CompletableFuture}</li>
 * </ul>
 *
 * @author martin
 * @version 2
 * @since 1
 */
@Getter
@Builder(builderMethodName = "newContainer")
public class HttpContainer {

    /**
     * Basic options
     */
    @Builder.Default private final String userAgent = "HttpClient/0.3.0";

    /**
     * Remote server options
     */
    @Builder.Default private final int port = 80;
    @Builder.Default private final Duration readTimeout = Duration.ofSeconds(10);
    @Builder.Default private final Duration connectTimeout = Duration.ofSeconds(10);
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
    public <T> HttpResponse<T> send(HttpRequest request, BodyType<T> bodyHandler) throws IOException {
        Scheme client = request.getHandler();

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
    public <T> CompletableFuture<HttpResponse<T>> sendAsync(HttpRequest request, BodyType<T> bodyHandler) throws IOException {
        Scheme client = request.getHandler();
        client.connect(InetAddress.getByName(host), port, https);

        CompletableFuture<HttpResponse<T>> future = new CompletableFuture<>();
        ExecutorService service = request.getExecutor();

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

    private <T> HttpResponse<T> parseResponse(String data, HttpRequest request, BodyType<T> bodyHandler) {
        if (request.getVersion() == HttpVersion.HTTP_2) {
            throw new UnsupportedOperationException("HTTP/2 protocol is not supported yet.");
        }

        WrappedHttpResponse wrapped = new DefaultScheme().parseResponse(request, data);

        // Redirect
        if ((wrapped.getStatus().getCode() >= 300 && wrapped.getStatus().getCode() <= 308) && !request.isDisableRedirects()) {
        }

        return new HttpResponse<T>() {
            @Override
            public HttpRequest request() {
                if (request.getUserAgent() == null)
                    request.setUserAgent(userAgent);

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
            public HttpHeaders headers() {
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
     * @since 1
     */
    public static class HttpContainerBuilder {
        public HttpContainerBuilder https() {
            this.https = true;
            return this;
        }
    }
}
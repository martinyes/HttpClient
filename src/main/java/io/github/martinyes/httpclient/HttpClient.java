package io.github.martinyes.httpclient;

import io.github.martinyes.httpclient.data.request.HttpRequest;
import io.github.martinyes.httpclient.data.response.HttpResponse;
import io.github.martinyes.httpclient.data.response.scheme.impl.DefaultScheme;
import io.github.martinyes.httpclient.net.impl.SocketClient;

import java.io.IOException;
import java.net.InetAddress;
import java.util.concurrent.CompletableFuture;

/**
 * HTTP Client.
 * <p>
 * This class is used to send one or multiple requests and parse their responses.
 * An HTTP client is created automatically through a request builder {@link HttpRequest.HttpRequestBuilder}
 * or can be created manually and send multiple requests with that specific HTTP Client.
 * <p>
 * The builder can be used to configure the request itself, like: protocol version,
 * HTTP request method, timeouts, remote host, message info, etc.
 * <p>
 * Requests can be sent in two ways: synchronously (blocking) or asynchronously (non-blocking).
 * <p>
 * {@link HttpClient#send(HttpRequest)} blocks until the request has been sent and the response has been received.
 * {@link HttpClient#sendAsync(HttpRequest)} sends the request and receives the response asynchronously.
 * It returns immediately with a {@link CompletableFuture<HttpResponse>}
 *
 * @author martin
 */
public class HttpClient {

    /**
     * This method used to send the given request synchronously.
     * It blocks the thread until the request has been send and a response has been received.
     *
     * @param request the configured request
     * @return a HTTP Response
     */
    public HttpResponse send(HttpRequest request) {
        return null;
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
        SocketClient client = new SocketClient(request.isHttps());
        client.connect(InetAddress.getByName(request.getHost()), request.getPort());

        CompletableFuture<HttpResponse> future = new CompletableFuture<>();

        request.getExecutor().submit(() -> {
            try {
                client.send(request);

                HttpResponse res = parseResponse(client.read(client.getIn()), request);
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
}
package cloud.lexium.httpclient;

import cloud.lexium.httpclient.data.request.HttpRequest;
import cloud.lexium.httpclient.data.response.HttpResponse;
import cloud.lexium.httpclient.data.response.impl.DefaultHttpResponse;
import cloud.lexium.httpclient.net.impl.SocketClient;

import java.io.IOException;
import java.net.InetAddress;
import java.util.concurrent.CompletableFuture;

/**
 * HTTP Client.
 * <p>
 * This class is used to send one or multiple requests and parse their responses.
 * An HTTP client is created automatically through a request builder {@link cloud.lexium.httpclient.data.request.HttpRequest.HttpRequestBuilder}
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

        return CompletableFuture.supplyAsync(() -> {
            try {
                return parseResponse(client.send(request), request);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }).whenComplete((__, ex) -> {
            if (ex != null)
                ex.printStackTrace();

            try {
                client.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private HttpResponse parseResponse(String data, HttpRequest request) throws IOException {
        /*
         * HTTP Response structure
         *
         * 1) status message
         * 2) an optional set of http headers
         * 3) a blank line indicating all meta-information
         * 4) an optional body content
         */

        String[] messages = data.split("\r\n");
        StringBuilder headerBuilder = new StringBuilder();
        StringBuilder bodyBuilder = new StringBuilder();

        for (String message : messages) {
            boolean blankLine = !message.matches("-*\\w.*"); // Checks for at least one ASCII char
        }

        return new DefaultHttpResponse(request, -1, null, null);
    }
}
package io.github.martinyes.httpclient;

import io.github.martinyes.httpclient.exception.RedirectLoopException;
import io.github.martinyes.httpclient.request.HttpRequest;
import io.github.martinyes.httpclient.response.HttpResponse;
import io.github.martinyes.httpclient.response.WrappedHttpResponse;
import io.github.martinyes.httpclient.response.body.BodyType;
import io.github.martinyes.httpclient.response.parser.impl.DefaultParser;
import io.github.martinyes.httpclient.scheme.Scheme;
import io.github.martinyes.httpclient.scheme.data.ConnectionData;
import io.github.martinyes.httpclient.scheme.data.response.RawResponse;
import io.github.martinyes.httpclient.scheme.data.response.impl.DefaultResponse;
import lombok.Builder;
import lombok.Getter;

import java.io.IOException;
import java.net.URI;
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
 * @version 3
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
    @Builder.Default private final Duration readTimeout = Duration.ofSeconds(10);
    @Builder.Default private final Duration connectTimeout = Duration.ofSeconds(10);

    /**
     * This method used to send the given request synchronously.
     * It blocks the thread until the request has been sent and a response has been received.
     *
     * @param request the configured request
     * @return an HTTP Response
     * @throws IOException           if an I/O error occurs when sending or receiving
     * @throws RedirectLoopException if there is a redirect loop
     */
    public <T> HttpResponse<T> send(HttpRequest request, BodyType<T> bodyHandler) throws IOException, RedirectLoopException {
        Scheme scheme = request.getScheme();

        scheme.connect(new ConnectionData(request.getUri(), this, request));
        HttpResponse<T> response = parseResponse(scheme.send(this, request), request, bodyHandler);
        scheme.disconnect();

        return response;
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
        Scheme scheme = request.getScheme();
        scheme.connect(new ConnectionData(request.getUri(), this, request));

        CompletableFuture<HttpResponse<T>> future = new CompletableFuture<>();
        ExecutorService service = request.getExecutor();

        service.submit(() -> {
            try {
                HttpResponse<T> res = parseResponse(scheme.send(this, request), request, bodyHandler);
                future.complete(res);
                scheme.disconnect();
            } catch (Throwable t) {
                t.printStackTrace();
                future.completeExceptionally(t);
            }
        });

        service.shutdown();

        return future;
    }

    private <T> HttpResponse<T> parseResponse(RawResponse response, HttpRequest request, BodyType<T> bodyType) throws IOException, RedirectLoopException {
        WrappedHttpResponse wrapped = new DefaultParser().parseResponse(request, response.data());

        // Redirect https://www.rfc-editor.org/rfc/rfc7231.html#section-6.4
        if ((wrapped.getStatus().getCode() >= 300 && wrapped.getStatus().getCode() <= 308) && request.isFollowRedirects()) {
            if (!HttpRequest.REDIRECTS_RULE.test(request.getRedirectsCompleted()))
                throw new RedirectLoopException(String.format("The http request exceeded the maximum number of redirects - %s", request.getRedirectsCompleted()));

            request.setRedirectsCompleted(request.getRedirectsCompleted() + 1);

            // TODO: fix parameters are not passed to the new request
            // possible fix: directly inject parameters into URI when sending the request through sockets.
            wrapped.getHeaders().print();
            String newPath = wrapped.getHeaders().get("Location").get(0);
            String withPortFormat = "%s://%s:%s%s";
            String withoutPortFormat = "%s://%s%s";
            String format = request.getUri().getPort() == -1
                    ? String.format(withoutPortFormat, request.getUri().getScheme(), request.getUri().getHost(), newPath)
                    : String.format(withPortFormat, request.getUri().getScheme(), request.getUri().getHost(), request.getUri().getPort(), newPath);

            request.setUri(URI.create(format));

            request.getScheme().disconnect();
            return this.send(request, bodyType);
        }

        // Check if the raw response is a default response so that we should not parse the response as it is not needed
        if (response instanceof DefaultResponse) {
            DefaultResponse temp = (DefaultResponse) response;

            return new HttpResponse<>() {
                @Override
                public HttpRequest request() {
                    if (request.getUserAgent() == null) request.setUserAgent(userAgent);

                    return request;
                }

                @Override
                public int statusCode() {
                    return temp.statusCode();
                }

                @Override
                public String protocol() {
                    // TODO: implement protocol
                    return request.getVersion().getHeaderName();
                }

                @Override
                public String statusText() {
                    return temp.statusText();
                }

                @Override
                public HttpHeaders headers() {
                    return temp.headers();
                }

                @Override
                public T body() {
                    return bodyType.apply(temp);
                }
            };
        }

        return new HttpResponse<>() {
            @Override
            public HttpRequest request() {
                if (request.getUserAgent() == null) request.setUserAgent(userAgent);

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
                return bodyType.apply(response);
            }
        };
    }
}
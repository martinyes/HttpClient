package io.github.martinyes.httpclient;

import io.github.martinyes.httpclient.response.HttpResponse;

import java.util.Map;

/**
 * A set of HTTP headers.
 * <p>
 * An <b>HttpHeaders</b> is not created directly, it is returned from an {@link HttpResponse}. Specific HTTP headers
 * can be set for a request through one of the request builder's headers methods.
 *
 * @author martin
 * @version 2
 * @since 2
 */
public class HttpHeaders {

    public static HttpHeaders of(Map<String, String> map) {
        return parse(map);
    }

    public Map<String, String> map() {
        return headers;
    }

    @Override
    public String toString() {
        return " { " + map() + " }";
    }

    private final Map<String, String> headers;

    private HttpHeaders(Map<String, String> map) {
        this.headers = map;
    }

    private static HttpHeaders parse(Map<String, String> headers) {
        // TODO: filtering and authenticating
        return new HttpHeaders(headers);
    }
}
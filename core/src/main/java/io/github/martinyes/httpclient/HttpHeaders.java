package io.github.martinyes.httpclient;

import io.github.martinyes.httpclient.response.HttpResponse;

import java.util.List;
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

    private final Map<String, List<String>> headers;

    public static HttpHeaders of(Map<String, List<String>> map) {
        return parse(map);
    }

    public List<String> get(String key) {
        return this.headers.get(key);
    }

    public void print() {
        this.headers.forEach((k, v) -> System.out.println(k + ":" + v));
    }

    public Map<String, List<String>> map() {
        return headers;
    }

    private HttpHeaders(Map<String, List<String>> map) {
        this.headers = map;
    }

    private static HttpHeaders parse(Map<String, List<String>> headers) {
        // TODO: filtering and authenticating
        return new HttpHeaders(headers);
    }

    @Override
    public String toString() {
        return " { " + map() + " }";
    }
}
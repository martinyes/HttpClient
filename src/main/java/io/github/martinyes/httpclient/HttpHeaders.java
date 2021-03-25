package io.github.martinyes.httpclient;

import java.util.Map;

/**
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
        final StringBuilder sb = new StringBuilder();
        sb.append(" { ");
        sb.append(map());
        sb.append(" }");
        return sb.toString();
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
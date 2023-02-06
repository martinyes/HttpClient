package io.github.martinyes.httpclient;

import com.google.common.collect.Maps;
import io.github.martinyes.httpclient.response.HttpResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Map;

/**
 * A set of HTTP headers.
 * <p>
 * An <b>HttpHeaders</b> is not created directly, it is returned from an {@link HttpResponse}. Specific HTTP headers
 * can be set for a request through one of the request builder's headers methods.
 *
 * @author martin
 * @version 3
 * @since 2
 */
public class HttpHeaders {

    private final Map<String, HeaderValue> headers;

    private HttpHeaders(Map<String, HeaderValue> map) {
        this.headers = map;
    }

    public static HttpHeaders of(Map<String, HeaderValue> map) {
        return parse(map);
    }

    public static HttpHeaders ofRawMap(Map<String, List<String>> map) {
        Map<String, HeaderValue> temp = Maps.newHashMap();

        map.forEach((key, values) -> temp.put(key, new HeaderValue(values)));

        return parse(temp);
    }

    public HeaderValue get(String key) {
        return this.headers.get(key);
    }

    public boolean exists(String header) {
        return this.headers.containsKey(header);
    }

    public void print() {
        this.headers.forEach((k, v) -> System.out.println(k + ":" + v.print()));
    }

    public Map<String, HeaderValue> map() {
        return headers;
    }

    private static HttpHeaders parse(Map<String, HeaderValue> headers) {
        // TODO: filtering and authenticating
        return new HttpHeaders(headers);
    }

    @Override
    public String toString() {
        return " { " + map() + " }";
    }

    @AllArgsConstructor
    public static class HeaderValue {

        @Getter
        private final List<String> values;

        public String get(int index) {
            return values.get(index);
        }

        public String get(String value) {
            return values.stream().filter(v -> v.equals(value)).findFirst().orElse(null);
        }

        public String print() {
            // print strings from the list separated by a comma
            return values.stream().reduce((s1, s2) -> s1 + ", " + s2).orElse("");
        }

        public boolean contains(String value) {
            return values.contains(value);
        }
    }
}
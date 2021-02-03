package io.github.martinyes.httpclient.data.response.impl;

import io.github.martinyes.httpclient.data.request.HttpRequest;
import io.github.martinyes.httpclient.data.response.HttpResponse;
import lombok.AllArgsConstructor;

import java.util.Map;

/**
 * @author martin
 */
@AllArgsConstructor
public class DefaultHttpResponse implements HttpResponse {

    private HttpRequest request;
    private String protocol;
    private int statusCode;
    private String statusText;
    private Map<String, String> headers;
    private String body;

    @Override
    public HttpRequest request() {
        return request;
    }

    @Override
    public int statusCode() {
        return statusCode;
    }

    @Override
    public String protocol() {
        return protocol;
    }

    @Override
    public String statusText() {
        return statusText;
    }

    @Override
    public Map<String, String> headers() {
        return headers;
    }

    @Override
    public String body() {
        return body;
    }
}
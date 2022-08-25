package io.github.martinyes.httpclient.scheme.data.response.impl;

import io.github.martinyes.httpclient.HttpHeaders;
import io.github.martinyes.httpclient.scheme.data.response.RawResponse;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DefaultResponse implements RawResponse {

    private final int statusCode;
    private final String statusText;
    private final HttpHeaders headers;
    private final String message;

    @Override
    public String message() {
        return this.message;
    }

    public int statusCode() {
        return this.statusCode;
    }

    public String statusText() {
        return this.statusText;
    }

    public HttpHeaders headers() {
        return this.headers;
    }
}
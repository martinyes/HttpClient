package io.github.martinyes.httpclient.scheme.data.response.impl;

import io.github.martinyes.httpclient.scheme.data.response.RawResponse;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SocketResponse implements RawResponse {

    private final String message;

    @Override
    public String message() {
        return this.message;
    }
}
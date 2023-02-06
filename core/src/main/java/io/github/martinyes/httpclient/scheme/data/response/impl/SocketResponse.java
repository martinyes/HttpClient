package io.github.martinyes.httpclient.scheme.data.response.impl;

import io.github.martinyes.httpclient.scheme.data.response.RawResponse;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SocketResponse implements RawResponse {

    private final String data;

    @Override
    public String data() {
        return this.data;
    }
}
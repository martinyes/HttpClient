package io.github.martinyes.httpclient.data.response.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

/**
 * A class responsible for wrapping HTTP responses.
 *
 * @author martin
 * @since 2
 */
@AllArgsConstructor
@Getter
public class WrappedHttpResponse {

    private final WrappedHttpStatus status;
    private final Map<String, String> headers;
    private final StringBuilder body;

    @AllArgsConstructor
    @Getter
    public static class WrappedHttpStatus {
        private final String protocol;
        private final int code;
        private final String text;
    }
}
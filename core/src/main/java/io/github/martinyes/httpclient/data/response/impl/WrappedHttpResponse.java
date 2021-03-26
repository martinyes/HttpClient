package io.github.martinyes.httpclient.data.response.impl;

import io.github.martinyes.httpclient.HttpHeaders;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * A class responsible for wrapping HTTP responses.
 *
 * @author martin
 * @version 2
 * @since 2
 */
@AllArgsConstructor
@Getter
public class WrappedHttpResponse {

    private final WrappedHttpStatus status;
    private final HttpHeaders headers;
    private final StringBuilder body;

    @AllArgsConstructor
    @Getter
    public static class WrappedHttpStatus {
        private final String protocol;
        private final int code;
        private final String text;
    }
}
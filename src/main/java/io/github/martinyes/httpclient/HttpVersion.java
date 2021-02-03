package io.github.martinyes.httpclient;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The version of the HTTP protocol.
 *
 * @author martin
 */
@AllArgsConstructor
@Getter
public enum HttpVersion {

    /**
     * HTTP version 1.0
     */
    HTTP_1("HTTP/1.0"),
    /**
     * HTTP version 1.1
     */
    HTTP_1_1("HTTP/1.1"),
    /**
     * HTTP version 2
     */
    HTTP_2("HTTP/2");

    private final String headerName;
}
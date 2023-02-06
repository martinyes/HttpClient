package io.github.martinyes.httpclient.response.parser;

import io.github.martinyes.httpclient.HttpHeaders;
import io.github.martinyes.httpclient.request.HttpRequest;
import io.github.martinyes.httpclient.response.WrappedHttpResponse;

/**
 * An interface that supports more ways to handle the response.
 *
 * @author martin
 * @since 1
 */
public interface ResponseParser {

    /**
     * Parses the response headers.
     *
     * @param p0 the response data
     * @return the parsed response headers
     */
    HttpHeaders parseHeaders(String p0);

    /**
     * Parses the whole response.
     *
     * @param p0 the request that initiated the response
     * @param p1 the response data
     * @return the parsed response
     */
    WrappedHttpResponse parseResponse(HttpRequest p0, String p1);
}
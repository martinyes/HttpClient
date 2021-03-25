package io.github.martinyes.httpclient.data.response.scheme;

import io.github.martinyes.httpclient.HttpHeaders;
import io.github.martinyes.httpclient.data.request.HttpRequest;
import io.github.martinyes.httpclient.data.response.impl.WrappedHttpResponse;

import java.util.Map;

/**
 * An interface that supports more ways to handle the response.
 *
 * @author martin
 * @since 1
 */
public interface ResponseScheme {

    HttpHeaders parseHeaders(String p0);

    WrappedHttpResponse parseResponse(HttpRequest p0, String p1);
}
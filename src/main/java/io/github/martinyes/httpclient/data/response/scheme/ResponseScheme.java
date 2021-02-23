package io.github.martinyes.httpclient.data.response.scheme;

import io.github.martinyes.httpclient.data.request.HttpRequest;
import io.github.martinyes.httpclient.data.response.impl.WrappedHttpResponse;

import java.util.Map;

/**
 * @author martin
 */
public interface ResponseScheme {

    Map<String, String> parseHeaders(String p0);

    WrappedHttpResponse parseResponse(HttpRequest p0, String p1);
}
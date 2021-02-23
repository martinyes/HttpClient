package io.github.martinyes.httpclient.data.response.scheme.impl;

import io.github.martinyes.httpclient.data.request.HttpRequest;
import io.github.martinyes.httpclient.data.response.impl.WrappedHttpResponse;
import io.github.martinyes.httpclient.data.response.scheme.ResponseScheme;

import java.util.Map;

/**
 * Chunked implementation to parse responses sent in chunks using the {@link ResponseScheme } interface.
 *
 * @author martin
 */
public class ChunkedScheme extends DefaultScheme {

    @Override
    public WrappedHttpResponse parseResponse(HttpRequest request, String data) {
        Map<String, String> headers = parseHeaders(data);

        return null;
    }
}

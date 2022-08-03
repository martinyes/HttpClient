package io.github.martinyes.httpclient.response.scheme.impl;

import io.github.martinyes.httpclient.HttpHeaders;
import io.github.martinyes.httpclient.request.HttpRequest;
import io.github.martinyes.httpclient.response.WrappedHttpResponse;
import io.github.martinyes.httpclient.response.scheme.ResponseScheme;

/**
 * Chunked implementation to parse responses sent in chunks using the {@link ResponseScheme} interface.
 *
 * @author martin
 */
public class ChunkedScheme extends DefaultScheme {

    @Override
    public WrappedHttpResponse parseResponse(HttpRequest request, String data) {
        HttpHeaders headers = parseHeaders(data);

        return null;
    }
}

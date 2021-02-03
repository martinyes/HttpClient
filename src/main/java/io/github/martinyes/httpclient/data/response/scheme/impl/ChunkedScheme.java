package io.github.martinyes.httpclient.data.response.scheme.impl;

import io.github.martinyes.httpclient.data.request.HttpRequest;
import io.github.martinyes.httpclient.data.response.HttpResponse;

import java.util.Map;

/**
 * @author martin
 */
public class ChunkedScheme extends DefaultScheme {

    @Override
    public HttpResponse parseResponse(HttpRequest request, String data) {
        Map<String, String> headers = parseHeaders(data);

        return null;
    }
}

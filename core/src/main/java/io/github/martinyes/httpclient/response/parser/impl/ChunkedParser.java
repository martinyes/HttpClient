package io.github.martinyes.httpclient.response.parser.impl;

import io.github.martinyes.httpclient.HttpHeaders;
import io.github.martinyes.httpclient.request.HttpRequest;
import io.github.martinyes.httpclient.response.WrappedHttpResponse;
import io.github.martinyes.httpclient.response.parser.ResponseParser;

/**
 * Chunked implementation to parse responses sent in chunks using the {@link ResponseParser} interface.
 *
 * @author martin
 */
public class ChunkedParser extends DefaultParser {

    @Override
    public WrappedHttpResponse parseResponse(HttpRequest request, String data) {
        HttpHeaders headers = parseHeaders(data);

        // TODO: implement chunked parser

        return null;
    }
}

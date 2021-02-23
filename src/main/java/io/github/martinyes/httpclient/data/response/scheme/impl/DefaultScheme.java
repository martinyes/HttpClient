package io.github.martinyes.httpclient.data.response.scheme.impl;

import io.github.martinyes.httpclient.data.request.HttpRequest;
import io.github.martinyes.httpclient.data.response.impl.WrappedHttpResponse;
import io.github.martinyes.httpclient.data.response.scheme.ResponseScheme;

import java.util.HashMap;
import java.util.Map;

/**
 * Default implementation to parse responses using the {@link ResponseScheme} interface.
 *
 * @author martin
 */
public class DefaultScheme implements ResponseScheme {

    @Override
    public Map<String, String> parseHeaders(String data) {
        Map<String, String> headers = new HashMap<>();

        String[] messages = data.split("\r\n");

        for (String message : messages) {
            boolean blankLine = !message.matches("-*\\w.*");

            if (blankLine)
                break;

            String[] headerPair = message.split(": ", 2);
            if (headerPair.length == 2)
                headers.put(headerPair[0].toLowerCase(), headerPair[1]);
        }

        return headers;
    }

    @Override
    public WrappedHttpResponse parseResponse(HttpRequest request, String data) {
        Map<String, String> headers = parseHeaders(data);

        // Check whether the response is transferred by chunks or not.
        // If so, we simply change the parsing algorithm to the chunked one.
        if (headers.containsKey("Transfer-Encoding") &&
                headers.get("Transfer-Encoding").equals("chunked"))
            return new ChunkedScheme().parseResponse(request, data);

        String[] messages = data.split("\r\n");

        StringBuilder bodyBuilder = new StringBuilder();
        String[] statusLine = new String[3];
        boolean body = false;
        for (int i = 0; i < messages.length; i++) {
            boolean blankLine = !messages[i].matches("-*\\w.*");

            if (i == 0) {
                String[] status = messages[i].split(" ");

                statusLine[0] = status[0];
                statusLine[1] = status[1];
                statusLine[2] = status[2];
                continue;
            }

            if (blankLine) {
                body = true;
            }

            if (body) {
                bodyBuilder.append(bodyBuilder.toString().isEmpty() ? "" : "\r\n").append(messages[i]);
            }
        }

        return new WrappedHttpResponse(new WrappedHttpResponse.WrappedHttpStatus(statusLine[0], Integer.parseInt(statusLine[1]), statusLine[2]),
                headers, bodyBuilder);
    }
}
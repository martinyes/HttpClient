package cloud.lexium.httpclient.data.response.scheme.impl;

import cloud.lexium.httpclient.data.request.HttpRequest;
import cloud.lexium.httpclient.data.response.HttpResponse;
import cloud.lexium.httpclient.data.response.impl.DefaultHttpResponse;
import cloud.lexium.httpclient.data.response.scheme.ResponseScheme;

import java.util.HashMap;
import java.util.Map;

/**
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
    public HttpResponse parseResponse(HttpRequest request, String data) {
        Map<String, String> headers = parseHeaders(data);

        if (headers.containsValue("chunked"))
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

        return new DefaultHttpResponse(request, statusLine[0],
                Integer.parseInt(statusLine[1]), statusLine[2], headers, bodyBuilder.toString());
    }
}
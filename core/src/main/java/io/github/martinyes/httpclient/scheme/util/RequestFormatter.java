package io.github.martinyes.httpclient.scheme.util;

import io.github.martinyes.httpclient.HttpContainer;
import io.github.martinyes.httpclient.HttpVersion;
import io.github.martinyes.httpclient.request.HttpRequest;

public class RequestFormatter {

    public static String format(HttpContainer container, HttpRequest request) {
        final StringBuilder data = new StringBuilder();
        final String crlf = "\r\n";

        // startLine - METHOD TARGET VERSION
        String startLine = "%s %s %s";
        String queries = request.getParams().build(request);
        data.append(String.format(startLine, request.getMethod().name(), request.getUri().getPath() + queries,
                request.getVersion().getHeaderName())).append(crlf);

        // headers
        String host = "Host: %s";
        String connection = "Connection: close";
        String userAgent = "User-Agent: %s";

        if (request.getVersion().equals(HttpVersion.HTTP_1_1)) {
            connection = "Connection: keep-alive";
        }
        data.append(connection).append(crlf);
        data.append(String.format(host, request.getUri().getHost())).append(crlf);
        data.append(String.format(userAgent, (request.getUserAgent() == null ? container.getUserAgent() : request.getUserAgent())))
                .append(crlf);

        // custom headers
        StringBuilder headers = request.getHeaders().build(request);
        if (headers != null)
            data.append(headers);

        // body
        // TODO

        // endLine
        data.append(crlf).append(crlf);

        return data.toString();
    }
}
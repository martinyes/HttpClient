package io.github.martinyes.httpclient.scheme.impl;

import io.github.martinyes.httpclient.HttpContainer;
import io.github.martinyes.httpclient.HttpHeaders;
import io.github.martinyes.httpclient.request.HttpRequest;
import io.github.martinyes.httpclient.scheme.Scheme;
import io.github.martinyes.httpclient.scheme.data.ConnectionData;
import io.github.martinyes.httpclient.scheme.data.response.RawResponse;
import io.github.martinyes.httpclient.scheme.data.response.impl.DefaultResponse;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collection;
import java.util.Map;

public class DefaultScheme implements Scheme {

    private HttpURLConnection connection;

    @Override
    public void connect(ConnectionData data) throws IOException {
        HttpRequest request = data.getRequest();
        String queries = request.getParams().build(request);
        this.connection = (HttpURLConnection) new URL(data.getUri().toString() + queries).openConnection();

        connection.setConnectTimeout(data.getContainer()
                .getConnectTimeout().toMillisPart());
        connection.setReadTimeout(data.getContainer()
                .getReadTimeout().toMillisPart());
    }

    @Override
    public boolean disconnect() {
        try {
            connection.getInputStream().close();
            connection.getOutputStream().close();
            connection.getErrorStream().close();
            connection.disconnect();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public RawResponse send(HttpContainer container, HttpRequest request) throws IOException {
        connection.setRequestMethod(request.getMethod().name());

        Map<String, Collection<String>> headers = request.getHeaders().getHeadersMap().asMap();
        for (Map.Entry<String, Collection<String>> entry : headers.entrySet()) {
            connection.setRequestProperty(entry.getKey(), String.join(", ", entry.getValue()));
        }

        String body = this.read(connection.getInputStream());
        return new DefaultResponse(
                connection.getResponseCode(),
                connection.getResponseMessage(),
                HttpHeaders.of(connection.getHeaderFields()),
                body
        );
    }
}
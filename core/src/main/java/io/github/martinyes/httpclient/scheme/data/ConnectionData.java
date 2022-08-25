package io.github.martinyes.httpclient.scheme.data;

import io.github.martinyes.httpclient.HttpContainer;
import io.github.martinyes.httpclient.request.HttpRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.net.URI;

@Getter
@AllArgsConstructor
public class ConnectionData {

    private URI uri;
    private final HttpContainer container;
    private final HttpRequest request;

    public int getPort() {
        return isHttps() ? 443 : 80;
    }

    public boolean isHttps() {
        return this.uri.getScheme().equals("https");
    }
}
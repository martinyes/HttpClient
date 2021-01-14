package cloud.lexium.httpclient.data.response.impl;

import cloud.lexium.httpclient.data.request.HttpRequest;
import cloud.lexium.httpclient.data.response.IHttpResponse;
import lombok.AllArgsConstructor;

import java.util.Map;

@AllArgsConstructor
public class HttpResponseImpl implements IHttpResponse {

    private HttpRequest request;
    private int statusCode;
    private Map<String, String> headers;
    private String body;

    @Override
    public HttpRequest request() {
        return request;
    }

    @Override
    public int statusCode() {
        return statusCode;
    }

    @Override
    public int contentLength() {
        return Integer.parseInt(headers.get("Content-Length"));
    }

    @Override
    public String protocol() {
        return request.getVersion().getHeaderName();
    }

    @Override
    public Map<String, String> headers() {
        return headers;
    }

    @Override
    public String body() {
        return body;
    }
}
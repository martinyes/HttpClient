package io.github.martinyes.httpclient.data.response;

import io.github.martinyes.httpclient.data.request.HttpRequest;

import java.util.Map;

/**
 * @author martin
 */
public interface HttpResponse {

    HttpRequest request();

    int statusCode();

    String protocol();

    String statusText();

    Map<String, String> headers();

    String body();
}
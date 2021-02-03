package io.github.martinyes.httpclient.data.response.scheme;

import io.github.martinyes.httpclient.data.request.HttpRequest;
import io.github.martinyes.httpclient.data.response.HttpResponse;

import java.util.Map;

/**
 * @author martin
 */
public interface ResponseScheme {

    Map<String, String> parseHeaders(String p0);

    HttpResponse parseResponse(HttpRequest p0, String p1);
}
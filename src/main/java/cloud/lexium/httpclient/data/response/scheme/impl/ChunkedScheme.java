package cloud.lexium.httpclient.data.response.scheme.impl;

import cloud.lexium.httpclient.data.request.HttpRequest;
import cloud.lexium.httpclient.data.response.HttpResponse;

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

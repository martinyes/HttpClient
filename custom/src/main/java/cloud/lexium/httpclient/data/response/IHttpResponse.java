package cloud.lexium.httpclient.data.response;

import cloud.lexium.httpclient.data.request.HttpRequest;

import java.util.Map;

/*@Getter
@Setter
public class HttpResponse {

    private HttpRequest request;
    private int statusCode;
    private int contentLength;
    private String protocol;
    private String header;
    private String body;
}*/
public interface IHttpResponse {

    HttpRequest request();

    int statusCode();

    int contentLength();

    String protocol();

    Map<String, String> headers();

    String body();
}
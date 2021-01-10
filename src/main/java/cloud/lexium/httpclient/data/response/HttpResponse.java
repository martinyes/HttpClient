package cloud.lexium.httpclient.data.response;

import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HttpResponse {

    private int statusCode;
    private String protocolVersion;
    private JsonObject header;
    private JsonObject body;
}
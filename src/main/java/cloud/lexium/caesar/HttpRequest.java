package cloud.lexium.caesar;

import cloud.lexium.caesar.data.HttpMethod;
import cloud.lexium.caesar.data.response.HttpResponse;
import lombok.Builder;
import lombok.Getter;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;

@Builder
@Getter
public class HttpRequest {

    private final String host;
    private final int port;
    private final HttpMethod method;
    private final String path;
    private final Duration timeout;

    public CompletableFuture<HttpResponse> execute() {
        try {
            return new HttpClient().sendRequest(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
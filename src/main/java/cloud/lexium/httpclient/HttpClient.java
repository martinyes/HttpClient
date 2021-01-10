package cloud.lexium.httpclient;

import cloud.lexium.httpclient.data.response.HttpResponse;
import cloud.lexium.httpclient.net.SocketClient;

import java.io.IOException;
import java.net.InetAddress;
import java.util.concurrent.CompletableFuture;

public class HttpClient {

    private static final SocketClient client = new SocketClient();

    public HttpResponse sendRequest(HttpRequest request) throws Exception {
        client.connect(InetAddress.getByName(request.getHost()), (request.getPort() == 0 ? 80 : request.getPort()));

        CompletableFuture<HttpResponse> future = CompletableFuture.supplyAsync(() -> {
            try {
                return toResponse(client.send(request));
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }).whenComplete((__, ex) -> {
            if (ex != null)
                ex.printStackTrace();

            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        return future.get();
    }

    private HttpResponse toResponse(String responseData) throws IOException {
        HttpResponse response = new HttpResponse();
        response.setStatusCode(200);

        return response;
    }
}
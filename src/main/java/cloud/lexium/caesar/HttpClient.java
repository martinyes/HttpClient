package cloud.lexium.caesar;

import cloud.lexium.caesar.data.response.HttpResponse;
import cloud.lexium.caesar.net.SocketClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;

import java.net.InetAddress;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpClient {

    @Getter private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .serializeNulls()
            .create();
    @Getter private static final ExecutorService executor = Executors.newFixedThreadPool(4);

    public CompletableFuture<HttpResponse> sendRequest(HttpRequest request) throws Exception {
        CompletableFuture<HttpResponse> future = new CompletableFuture<>();

        SocketClient client = new SocketClient();
        client.connect(InetAddress.getByName(request.getHost()), (request.getPort() == 0 ? 80 : request.getPort()));
        client.close();

        future.complete(toResponse());

        return future;
    }

    private HttpResponse toResponse() {
        HttpResponse response = new HttpResponse();



        return response;
    }
}
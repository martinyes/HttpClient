package cloud.lexium.httpclient;

import cloud.lexium.httpclient.data.response.HttpResponse;
import cloud.lexium.httpclient.data.response.impl.HttpResponseImpl;
import cloud.lexium.httpclient.net.SocketClient;

import java.io.IOException;
import java.net.InetAddress;
import java.util.concurrent.CompletableFuture;

public class HttpClient {

    public HttpResponse sendRequest(HttpRequest request) throws Exception {
        SocketClient client = new SocketClient(request.isHttps());

        client.connect(InetAddress.getByName(request.getHost()), request.getPort());

        CompletableFuture<HttpResponse> future = CompletableFuture.supplyAsync(() -> {
            try {
                return parseResponse(client.send(request), request);
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

    private HttpResponse parseResponse(String data, HttpRequest request) throws IOException {
        /*
         * HTTP Response structure
         *
         * 1) status message
         * 2) an optional set of http headers
         * 3) a blank line indicating all meta-information
         * 4) an optional body content
         */

        String[] messages = data.split("\r\n");
        StringBuilder headerBuilder = new StringBuilder();
        StringBuilder bodyBuilder = new StringBuilder();

        System.out.println(data);

        for (String message : messages) {
            boolean blankLine = !message.matches("-*\\w.*"); // Checks for at least one ASCII char


        }

        return new HttpResponseImpl(request, -1, null, null);
    }
}
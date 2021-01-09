package test;

import cloud.lexium.caesar.HttpRequest;
import cloud.lexium.caesar.data.HttpMethod;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;

public class CaesarTest {

    public static void main(String[] args) throws IOException {
        // Caesar
        HttpRequest.builder()
                .host("localhost")
                .port(8080)
                .method(HttpMethod.GET)
                .timeout(Duration.ofSeconds(10))
                .build()
                .execute()
                .whenComplete((res, ex) -> {
                    System.out.println("Caesar: " + res.getBody());
                });

        // HttpClient
        /*URL url = null;
        try {
            url = new URL("http://127.0.0.1:8080/get");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("GET");
            con.setConnectTimeout(10_000);
            con.setReadTimeout(10_000);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null)
                content.append(inputLine);

            in.close();
            con.disconnect();

            System.out.println("HttpClient: " + content.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }
}
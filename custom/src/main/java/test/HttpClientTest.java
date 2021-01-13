package test;

import cloud.lexium.httpclient.HttpRequest;
import cloud.lexium.httpclient.data.HttpMethod;
import cloud.lexium.httpclient.data.HttpVersion;

public class HttpClientTest {

    public static void main(String[] args) {
        // Custom
        HttpRequest.builder()
                .host("mineside.hu")
                .https()
                .method(HttpMethod.GET)
                .path("/get")
                .version(HttpVersion.HTTP_1_1)
                .build()
                .execute()
                .whenComplete((res, ex) -> {
                    System.out.println("Custom: " + res.statusCode());
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
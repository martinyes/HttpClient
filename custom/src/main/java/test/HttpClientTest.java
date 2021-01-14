package test;

import cloud.lexium.httpclient.data.HttpMethod;
import cloud.lexium.httpclient.data.HttpVersion;
import cloud.lexium.httpclient.data.request.HttpRequest;

public class HttpClientTest {

    public static void main(String[] args) {
        // Custom
        HttpRequest.builder()
                .host("postman-echo.com")
                .https()
                .method(HttpMethod.GET)
                .path("/get")
                .params("key1", "value1", "key1", "value2")
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
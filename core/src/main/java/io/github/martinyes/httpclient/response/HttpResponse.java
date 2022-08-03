package io.github.martinyes.httpclient.response;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.github.martinyes.httpclient.HttpHeaders;
import io.github.martinyes.httpclient.request.HttpRequest;
import io.github.martinyes.httpclient.response.body.BodyType;
import io.github.martinyes.httpclient.response.body.impl.ByteArrayBodyType;

import java.nio.charset.Charset;

/**
 * A class represents a response of a {@link HttpRequest}.
 *
 * @param <T> response body type
 * @author martin
 * @since 1
 */
public interface HttpResponse<T> {

    HttpRequest request();

    int statusCode();

    String protocol();

    String statusText();

    HttpHeaders headers();

    T body();

    /**
     * A class represents some factory methods for body types.
     *
     * @author martin
     * @since 2
     */
    class BodyHandlers {

        public static BodyType<String> asString() {
            return res -> res.getBody().toString();
        }

        public static BodyType<byte[]> asBytes() {
            return new ByteArrayBodyType();
        }

        public static BodyType<byte[]> asBytes(Charset charset) {
            return new ByteArrayBodyType(charset);
        }

        public static BodyType<JsonObject> asJson() {
            return res -> JsonParser.parseString(res.getBody().toString()).getAsJsonObject();
        }
    }
}
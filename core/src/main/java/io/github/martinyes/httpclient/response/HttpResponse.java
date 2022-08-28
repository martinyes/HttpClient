package io.github.martinyes.httpclient.response;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.github.martinyes.httpclient.HttpHeaders;
import io.github.martinyes.httpclient.request.HttpRequest;
import io.github.martinyes.httpclient.response.body.BodyType;
import io.github.martinyes.httpclient.response.body.impl.ByteArrayBodyType;
import io.github.martinyes.httpclient.scheme.data.response.RawResponse;

import java.nio.charset.Charset;

/**
 * A class represents a response from the server.
 *
 * @param <T> response body type
 * @author martin
 * @version 2
 * @since 1
 */
public interface HttpResponse<T> {

    /**
     * @return the request that was initiated to get this response
     */
    HttpRequest request();

    /**
     * @return the response code.
     */
    int statusCode();

    /**
     * @return the response protocol.
     */
    String protocol();

    /**
     * @return the text representation of the response code.
     */
    String statusText();

    /**
     * @return the response headers.
     */
    HttpHeaders headers();

    /**
     * @return the response body
     */
    T body();

    /**
     * A class represents some factory methods for body types.
     *
     * @author martin
     * @since 2
     */
    class BodyHandlers {

        public static BodyType<String> asString() {
            return RawResponse::data;
        }

        public static BodyType<byte[]> asBytes() {
            return new ByteArrayBodyType();
        }

        public static BodyType<byte[]> asBytes(Charset charset) {
            return new ByteArrayBodyType(charset);
        }

        public static BodyType<JsonObject> asJson() {
            return res -> JsonParser.parseString(res.data()).getAsJsonObject();
        }
    }
}
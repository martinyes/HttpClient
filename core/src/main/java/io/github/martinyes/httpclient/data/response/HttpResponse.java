package io.github.martinyes.httpclient.data.response;

import io.github.martinyes.httpclient.HttpHeaders;
import io.github.martinyes.httpclient.data.request.HttpRequest;
import io.github.martinyes.httpclient.data.response.body.ByteArrayBodyHandler;
import lombok.experimental.UtilityClass;

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

        public static BodyHandler<String> asString() {
            return res -> res.getBody().toString();
        }

        public static BodyHandler<byte[]> asBytes() {
            return new ByteArrayBodyHandler();
        }

        public static BodyHandler<byte[]> asBytes(Charset charset) {
            return new ByteArrayBodyHandler(charset);
        }
    }
}
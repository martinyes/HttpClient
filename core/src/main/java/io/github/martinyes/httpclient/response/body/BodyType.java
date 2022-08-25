package io.github.martinyes.httpclient.response.body;

import io.github.martinyes.httpclient.HttpContainer;
import io.github.martinyes.httpclient.response.WrappedHttpResponse;
import io.github.martinyes.httpclient.scheme.data.response.RawResponse;

/**
 * A handler for response bodies. This class provides ability to implement many common body types.
 * <p>
 * A BodyType takes a {@link WrappedHttpResponse} which contains everything about a response.
 * It is invoked once we are parsing the response in {@link HttpContainer}.
 * <p>
 * There are various <b>predefined</b> body types like:
 * <ul>
 *  <li><b>String Body</b></li>
 *  <li><b>Bytes Body</b></li>
 *  <li><b>Json Body</b></li>
 * </ul>
 *
 * <pre>
 *     {@code
 *     // Receives the response body as String
 *     HttpResponse<String> response = client.send(request, HttpResponse.BodyType.asString());
 *
 *     // Receives the response body as bytes
 *     HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyType.asBytes());
 *     }</pre>
 *
 * @param <T> response body type
 * @author martin
 * @since 2
 * @version 2
 */
@FunctionalInterface
public interface BodyType<T> {

    T apply(RawResponse p0);
}
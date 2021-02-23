package io.github.martinyes.httpclient.data.response;

import io.github.martinyes.httpclient.HttpClient;
import io.github.martinyes.httpclient.data.response.body.ByteArrayBodyHandler;
import io.github.martinyes.httpclient.data.response.body.StringBodyHandler;
import io.github.martinyes.httpclient.data.response.impl.WrappedHttpResponse;

/**
 * A handler for response bodies. This class provides ability to implement many common body types.
 * <p>
 * A BodyHandler takes a {@link WrappedHttpResponse} which contains everything about a response.
 * It is invoked once we are parsing the response in {@link HttpClient}.
 * <p>
 * There are various <b>predefined</b> body handlers like:
 * <ul>
 *  <li>String BodyHandler</li>
 *      HttpResponse<String> response = client
 *          .send(request, new {@link StringBodyHandler}());
 *  <li>Byte Array BodyHandler</li>
 *      HttpResponse<byte[]> response = client
 *          .send(request, new {@link ByteArrayBodyHandler}());
 *  <li>File BodyHandler</li>
 *      HttpResponse<Path> response = client
 *          .send(request, new PathBodyHandler())
 * </ul>
 *
 * @param <T>
 * @author martin
 */
@FunctionalInterface
public interface BodyHandler<T> {

    T apply(WrappedHttpResponse p0);
}
package io.github.martinyes.httpclient.scheme;

import io.github.martinyes.httpclient.HttpContainer;
import io.github.martinyes.httpclient.request.HttpRequest;
import io.github.martinyes.httpclient.scheme.data.ConnectionData;
import io.github.martinyes.httpclient.scheme.data.response.RawResponse;

import java.io.IOException;
import java.io.InputStream;

/**
 * This class provides the flexibility to create more connecting schemes, not only a Socket one.
 * <p>
 * {@link HttpContainer} uses Socket as its default connecting scheme,
 * but you can specify a different one by implementing this interface.
 *
 * @author martin
 * @since 1
 * @version 2
 */
public interface Scheme {

    void connect(ConnectionData p0) throws IOException;

    boolean disconnect();

    RawResponse send(HttpContainer p0, HttpRequest p1) throws IOException;

    default String read(InputStream in) throws IOException {
        final StringBuilder builder = new StringBuilder();
        final byte[] buffer = new byte[4096];
        int read;
        while ((read = in.read(buffer)) > 0)
            builder.append(new String(buffer, 0, read));

        return builder.toString();
    }
}
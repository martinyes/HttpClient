package io.github.martinyes.httpclient.scheme;

import io.github.martinyes.httpclient.HttpContainer;
import io.github.martinyes.httpclient.request.HttpRequest;
import io.github.martinyes.httpclient.scheme.data.ConnectionData;
import io.github.martinyes.httpclient.scheme.data.response.RawResponse;
import io.github.martinyes.httpclient.scheme.impl.UrlConnectionScheme;

import java.io.IOException;
import java.io.InputStream;

/**
 * This class provides the flexibility to create more connecting schemes, not only a Socket one.
 * <p>
 * {@link HttpRequest} uses {@link UrlConnectionScheme} as its default connecting scheme,
 * but you can specify a different one by implementing this interface.
 *
 * @author martin
 * @since 1
 * @version 2
 */
public interface Scheme {

    /**
     * Connects to the server.
     * <p>
     * @param data the connection data
     * @throws IOException if an I/O error occurs
     */
    void connect(ConnectionData data) throws IOException;

    /**
     * Disconnects from the server.
     * <p>
     * @return true if the disconnection was successful, false otherwise
     */
    boolean disconnect();

    /**
     * Sends the request to the server.
     * <p>
     * @param request the request to send
     * @param container the container to use
     * @return the response from the server
     * @throws IOException if an I/O error occurs
     */
    RawResponse send(HttpContainer container, HttpRequest request) throws IOException;

    /**
     * Read the response from the server.
     * <p>
     * @param in the input stream to read from
     * @return the response from the server
     * @throws IOException if an I/O error occurs
     */
    default String read(InputStream in) throws IOException {
        final StringBuilder builder = new StringBuilder();
        final byte[] buffer = new byte[4096];
        int read;
        while ((read = in.read(buffer)) > 0)
            builder.append(new String(buffer, 0, read));

        return builder.toString();
    }
}
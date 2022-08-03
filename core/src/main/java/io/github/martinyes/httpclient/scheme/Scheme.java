package io.github.martinyes.httpclient.scheme;

import io.github.martinyes.httpclient.HttpContainer;
import io.github.martinyes.httpclient.request.HttpRequest;

import java.io.IOException;
import java.net.InetAddress;

/**
 * This class provides the flexibility to create more connecting schemes, not only a Socket one.
 * <p>
 * {@link HttpContainer} uses Socket as its default connecting scheme,
 * but you can specify a different one by implementing this interface.
 *
 * @author martin
 * @since 1
 */
public interface Scheme {

    void connect(InetAddress p0, int p1, boolean p2) throws IOException;

    void disconnect() throws IOException;

    void send(HttpContainer p0, HttpRequest p1) throws IOException;

    String read() throws IOException;
}
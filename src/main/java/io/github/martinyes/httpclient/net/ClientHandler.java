package io.github.martinyes.httpclient.net;

import io.github.martinyes.httpclient.HttpClient;
import io.github.martinyes.httpclient.data.request.HttpRequest;

import java.io.IOException;
import java.net.InetAddress;

/**
 * This class provides the flexibility to create more connecting schemes, not only a Socket one.
 * <p>
 * {@link HttpClient} uses Socket as its default connecting scheme
 * but you can specify a different one by implementing this interface.
 *
 * @author martin
 * @since 1
 */
public interface ClientHandler {

    void connect(InetAddress p0, int p1, boolean p2) throws IOException;

    void disconnect() throws IOException;

    void send(HttpClient p0, HttpRequest p1) throws IOException;

    String read() throws IOException;
}
package io.github.martinyes.httpclient.net;

import io.github.martinyes.httpclient.data.request.HttpRequest;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.InetAddress;

/**
 * @author martin
 */
public interface ClientHandler {

    void connect(InetAddress p0, int p1) throws IOException;

    void disconnect() throws IOException;

    void send(HttpRequest p0) throws IOException;

    String read(BufferedInputStream p0) throws IOException;
}
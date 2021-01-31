package cloud.lexium.httpclient.net;

import cloud.lexium.httpclient.data.request.HttpRequest;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.InetAddress;

/**
 * @author martin
 */
public interface NetHandler {

    void connect(InetAddress p0, int p1) throws IOException;

    void disconnect() throws IOException;

    String send(HttpRequest p0) throws IOException;

    String read(BufferedInputStream p0) throws IOException;
}
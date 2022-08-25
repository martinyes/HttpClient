package io.github.martinyes.httpclient.scheme.impl;

import io.github.martinyes.httpclient.HttpContainer;
import io.github.martinyes.httpclient.HttpVersion;
import io.github.martinyes.httpclient.request.HttpRequest;
import io.github.martinyes.httpclient.scheme.Scheme;
import io.github.martinyes.httpclient.scheme.data.ConnectionData;
import io.github.martinyes.httpclient.scheme.data.response.RawResponse;
import io.github.martinyes.httpclient.scheme.data.response.impl.SocketResponse;
import lombok.Getter;

import javax.net.ssl.SSLSocketFactory;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import static io.github.martinyes.httpclient.scheme.util.RequestFormatter.format;

/**
 * An implementation to {@link Scheme} using Java Sockets.
 * <p>
 * This class provides basic I/O operations to send headers and get responses through Sockets.
 *
 * @author martin
 * @version 4
 * @since 1
 */
@Getter
public class SocketScheme implements Scheme {

    private Socket serverSocket;
    private BufferedOutputStream out;
    private BufferedInputStream in;

    @Override
    public void connect(ConnectionData data) throws IOException {
        int portToUse = data.getUri().getPort() == -1 ? data.getPort() : data.getUri().getPort();
        if (data.isHttps()) {
            SSLSocketFactory sslSocketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            this.serverSocket = sslSocketFactory.createSocket(data.getUri().getHost(), portToUse);
        } else {
            this.serverSocket = new Socket(data.getUri().getHost(), portToUse);
        }

        this.out = new BufferedOutputStream(serverSocket.getOutputStream());
        this.in = new BufferedInputStream(serverSocket.getInputStream());
    }

    @Override
    public boolean disconnect() {
        try {
            this.out.close();
            this.in.close();
            this.serverSocket.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public RawResponse send(HttpContainer container, HttpRequest request) throws IOException {
        if (request.getVersion().equals(HttpVersion.HTTP_2))
            throw new UnsupportedOperationException("HTTP/2 protocol is not supported in Socket scheme yet.");

        out.write(format(container, request).getBytes(StandardCharsets.UTF_8));
        out.flush();

        String message = this.read(this.in);
        return new SocketResponse(
                message
        );
    }
}
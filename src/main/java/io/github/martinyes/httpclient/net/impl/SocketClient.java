package io.github.martinyes.httpclient.net.impl;

import io.github.martinyes.httpclient.HttpClient;
import io.github.martinyes.httpclient.data.request.HttpRequest;
import io.github.martinyes.httpclient.net.ClientHandler;
import lombok.Getter;

import javax.net.ssl.SSLSocketFactory;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * An implementation to {@link ClientHandler} using Java Sockets.
 * <p>
 * This class provides basic I/O operations to send headers and get responses through Sockets.
 *
 * @author martin
 * @version 2
 * @since 1
 */
@Getter
public class SocketClient implements ClientHandler {

    public boolean https;

    private Socket serverSocket;
    private BufferedOutputStream out;
    private BufferedInputStream in;

    @Override
    public void connect(InetAddress address, int port, boolean https) throws IOException {
        this.https = https;

        int portToUse = port == 80 ? getDefaultPort() : port;
        if (https) {
            SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            this.serverSocket = sslsocketfactory.createSocket(address, portToUse);
        } else {
            this.serverSocket = new Socket(address, portToUse);
        }

        this.out = new BufferedOutputStream(serverSocket.getOutputStream());
        this.in = new BufferedInputStream(serverSocket.getInputStream());
    }

    @Override
    public void disconnect() throws IOException {
        this.out.close();
        this.in.close();
        this.serverSocket.close();
    }

    @Override
    public void send(HttpClient client, HttpRequest request) throws IOException {
        String rawHeader = "%s %s\r\nConnection: close\r\nHost:%s\r\n%s\r\n\r\n";
        String queryParam = request.getParams().build(request);

        out.write(String.format(rawHeader, request.getMethod().name() + " " + request.getPath() + queryParam,
                request.getVersion().getHeaderName(), client.getHost() + ":" + getDefaultPort(),
                request.getHeaders().build(request).toString())
                .getBytes(StandardCharsets.UTF_8));
        out.flush();
    }

    @Override
    public String read() throws IOException {
        final StringBuilder builder = new StringBuilder();
        final byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) > 0) {
            builder.append(new String(buffer, 0, read));
        }

        return builder.toString();
    }

    private int getDefaultPort() {
        return https ? 443 : 80;
    }
}
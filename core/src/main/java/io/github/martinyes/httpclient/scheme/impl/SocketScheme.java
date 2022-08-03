package io.github.martinyes.httpclient.scheme.impl;

import io.github.martinyes.httpclient.HttpContainer;
import io.github.martinyes.httpclient.request.HttpRequest;
import io.github.martinyes.httpclient.scheme.Scheme;
import lombok.Getter;

import javax.net.ssl.SSLSocketFactory;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * An implementation to {@link Scheme} using Java Sockets.
 * <p>
 * This class provides basic I/O operations to send headers and get responses through Sockets.
 *
 * @author martin
 * @version 3
 * @since 1
 */
@Getter
public class SocketScheme implements Scheme {

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
    public void send(HttpContainer client, HttpRequest request) throws IOException {
        out.write(getFormedData(client, request).getBytes(StandardCharsets.UTF_8));
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

    private String getFormedData(HttpContainer client, HttpRequest request) {
        final StringBuilder data = new StringBuilder();
        final String crlf = "\r\n";

        // startLine - METHOD TARGET VERSION
        String startLine = "%s %s %s";
        String queries = request.getParams().build(request);
        data.append(String.format(startLine, request.getMethod().name(), request.getPath() + queries,
                request.getVersion().getHeaderName())).append(crlf);

        // headers
        String host = "Host: %s";
        String connection = "Connection: close";
        String userAgent = "User-Agent: %s";
        data.append(connection).append(crlf);
        data.append(String.format(host, client.getHost() + ":" + getDefaultPort())).append(crlf);
        data.append(String.format(userAgent, (request.getUserAgent() == null ? client.getUserAgent() : request.getUserAgent())))
                .append(crlf);

        StringBuilder headers = request.getHeaders().build(request);
        if (headers != null)
            data.append(headers);

        // TODO: body

        // endLine
        data.append(crlf).append(crlf);

        return data.toString();
    }

    private int getDefaultPort() {
        return https ? 443 : 80;
    }
}
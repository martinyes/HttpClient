package io.github.martinyes.httpclient.net.impl;

import io.github.martinyes.httpclient.data.request.HttpRequest;
import io.github.martinyes.httpclient.data.request.ParamProcessor;
import io.github.martinyes.httpclient.net.ClientHandler;
import lombok.Getter;

import javax.net.ssl.SSLSocketFactory;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * @author martin
 */
@Getter
public class SocketClient implements ClientHandler {

    public boolean useHttps;

    private Socket serverSocket;
    private OutputStream out;
    private BufferedInputStream in;

    public SocketClient(boolean useHttps) {
        this.useHttps = useHttps;
    }

    @Override
    public void connect(InetAddress address, int port) throws IOException {
        int portToUse = port == 80 ? getDefaultPort() : port;
        if (useHttps) {
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
    public void send(HttpRequest request) throws IOException {
        String rawHeader = "%s %s\r\nConnection: close\r\nHost:%s\r\n\r\n";
        String queryParam = ParamProcessor.buildQueryURL(request);

        out.write(String.format(rawHeader, request.getMethod().name() + " " + request.getPath() + queryParam,
                request.getVersion().getHeaderName(), request.getHost() + ":" + getDefaultPort())
                .getBytes(StandardCharsets.UTF_8));
        out.flush();
    }

    @Override
    public String read(BufferedInputStream in) throws IOException {
        final StringBuilder builder = new StringBuilder();
        final byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) > 0) {
            builder.append(new String(buffer, 0, read));
        }

        return builder.toString();
    }

    private int getDefaultPort() {
        return useHttps ? 443 : 80;
    }
}
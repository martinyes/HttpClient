package cloud.lexium.httpclient.net;

import cloud.lexium.httpclient.HttpRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

@Getter
public class SocketClient {

    public boolean useHttps;

    private Socket socket;
    private DataOutputStream out;
    private DataInputStream in;

    public SocketClient(boolean useHttps) {
        this.useHttps = useHttps;
    }

    public void connect(InetAddress host, int port) throws IOException {
        if (useHttps) {
            SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            this.socket = sslsocketfactory.createSocket(host, port);
        } else {
            this.socket = new Socket(host, port);
        }

        this.out = new DataOutputStream(socket.getOutputStream());
        this.in = new DataInputStream(socket.getInputStream());
    }

    public void close() throws IOException {
        this.out.close();
        this.in.close();
        this.socket.close();
    }

    public String send(HttpRequest request) throws IOException {
        String rawHeader = "%s HTTP/1.1\r\nConnection: close\r\nHost:%s\r\n\r\n";

        out.write(String.format(rawHeader, request.getMethod().name() + " " + request.getPath(), request.getHost() + ":" + request.getPort())
                .getBytes());

        return readAsString(in);
    }

    private String readAsString(DataInputStream inputStream) throws IOException {
        final StringBuilder builder = new StringBuilder();
        final byte[] buffer = new byte[1024];
        int read;
        while ((read = inputStream.read(buffer)) > 0) {
            builder.append(new String(buffer, 0, read));
        }

        return builder.toString();
    }
}
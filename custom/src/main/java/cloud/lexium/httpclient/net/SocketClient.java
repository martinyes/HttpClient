package cloud.lexium.httpclient.net;

import cloud.lexium.httpclient.HttpRequest;
import lombok.Getter;

import javax.net.ssl.SSLSocketFactory;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

@Getter
public class SocketClient {

    public boolean useHttps;

    private Socket serverSocket;
    private DataOutputStream out;
    private DataInputStream in;

    public SocketClient(boolean useHttps) {
        this.useHttps = useHttps;
    }

    public void connect(InetAddress host, int port) throws IOException {
        int portToUse = port == -1 ? getDefaultPort() : port;
        if (useHttps) {
            SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            this.serverSocket = sslsocketfactory.createSocket(host, portToUse);
        } else {
            this.serverSocket = new Socket(host, portToUse);
        }

        this.out = new DataOutputStream(serverSocket.getOutputStream());
        this.in = new DataInputStream(serverSocket.getInputStream());
    }

    public void close() throws IOException {
        this.out.close();
        this.in.close();
        this.serverSocket.close();
    }

    public int getDefaultPort() {
        return useHttps ? 443 : 80;
    }

    public String send(HttpRequest request) throws IOException {
        String rawHeader = "%s %s\r\nConnection: close\r\nHost:%s\r\n\r\n";

        out.write(String.format(rawHeader, request.getMethod().name() + " " + request.getPath(),
                request.getVersion().getHeaderName(), request.getHost() + ":" + getDefaultPort())
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
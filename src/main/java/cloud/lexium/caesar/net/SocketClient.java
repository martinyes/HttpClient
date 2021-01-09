package cloud.lexium.caesar.net;

import lombok.Getter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

@Getter
public class SocketClient {

    private Socket socket;
    private DataOutputStream out;
    private DataInputStream in;

    public void connect(InetAddress host, int port) throws IOException {
        this.socket = new Socket(host, port);
        this.out = new DataOutputStream(socket.getOutputStream());
        this.in = new DataInputStream(socket.getInputStream());

        out.write("GET /get HTTP/1.1\r\nConnection: close\r\nHost:localhost\r\n\r\n"
                .getBytes());
        String response = readAsString(in);

        System.out.println(response);
    }

    public void close() throws IOException {
        this.out.close();
        this.in.close();
        this.socket.close();
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
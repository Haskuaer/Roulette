package ef.client.util;

import java.io.*;
import java.net.Socket;

public class ClientSocket {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public ClientSocket(String host, int port) throws IOException {
        socket = new Socket(host, port);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void sendMessage(String message) throws IOException {
        out.println(message);
    }

    public String receiveMessage() throws IOException {
        return in.readLine();
    }

    public void close() throws IOException {
        if (socket != null) socket.close();
        if (in != null) in.close();
        if (out != null) out.close();
    }
}
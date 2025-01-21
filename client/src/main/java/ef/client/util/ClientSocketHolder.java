package ef.client.util;

//singleton structure for retriving clientsocket
public class ClientSocketHolder {

    private static ClientSocket clientSocket;

    public static ClientSocket getClientSocket() { return clientSocket; }
    public static void setClientSocket(ClientSocket clientSocket) { ClientSocketHolder.clientSocket = clientSocket; }
}

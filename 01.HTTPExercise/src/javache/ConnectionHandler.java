package javache;

import java.net.Socket;

public class ConnectionHandler extends Thread {
    private Socket clientSocket;
    private RequestHandler requestHandler;


    public ConnectionHandler(Socket clientSocket, RequestHandler requestHandler) {
        this.clientSocket = clientSocket;
        this.requestHandler = requestHandler;
    }
}

package javache;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Date;
import java.util.concurrent.FutureTask;

public class Server {
    private static final String LISTENING_MESSAGE = "Listening on port: ";
    private static final int SOCKET_TIMEOUT_MILLISECONDS = 5000;
    private static final String SOCKET_TIMEOUT_EXCEPTION_MESSAGE = "Socket timeout detection!";

    private int port;
    private int timeouts;

    private ServerSocket server;

    public Server(int port) {
        this.port = port;
        this.timeouts = 0;
    }

    public void run() throws IOException {
        try {
            this.server = new ServerSocket(this.port);
            System.out.println(LISTENING_MESSAGE + this.port);

            while (true) {
                Socket clientSocket = this.server.accept();
                System.out.println("Connection opened. (" + new Date() + ")");
                ConnectionHandler connectionHandler = new ConnectionHandler(clientSocket, new RequestHandler());
                Thread thread = new Thread(connectionHandler);
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

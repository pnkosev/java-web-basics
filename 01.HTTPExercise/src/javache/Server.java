package javache;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.FutureTask;

public class Server {
    private static final int SOCKET_TIMEOUT_MILLISECONDS = 5000;
    private static final String SOCKET_TIMEOUT_EXCEPTION_MESSAGE = "Socket timeout detection!";
    private int port;
    private ServerSocket server;

    public Server() {
        this.port = WebConstants.SERVER_PORT ;
    }

    public void run() throws IOException {
        this.server = new ServerSocket(this.port);
        this.server.setSoTimeout(SOCKET_TIMEOUT_MILLISECONDS);

        while (true) {
            try (Socket clientSocket = this.server.accept()) {
                clientSocket.setSoTimeout(SOCKET_TIMEOUT_MILLISECONDS);

                ConnectionHandler connectionHandler = new ConnectionHandler(clientSocket, new RequestHandler());
                FutureTask<?> task = new FutureTask<>(connectionHandler, null);
                task.run();
            } catch (SocketTimeoutException e) {
                System.out.println(SOCKET_TIMEOUT_EXCEPTION_MESSAGE);
//                e.printStackTrace();
            }
        }
    }
}

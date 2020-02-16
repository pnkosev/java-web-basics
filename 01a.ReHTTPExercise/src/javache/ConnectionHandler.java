package javache;

import javache.io.Reader;
import javache.io.Writer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ConnectionHandler implements Runnable {
    private Socket clientSocket;
    private RequestHandler requestHandler;

    private InputStream inputStream;
    private OutputStream outputStream;


    public ConnectionHandler(Socket clientSocket, RequestHandler requestHandler) {
        this.initializeConnection(clientSocket);
        this.requestHandler = requestHandler;
    }

    @Override
    public void run() {
        try {
            String requestContent = Reader.readAllLines(this.inputStream);
            if (!requestContent.isEmpty()) {
                byte[] byteData = this.requestHandler.handleRequest(requestContent);
                Writer.writeBytes(byteData, this.outputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                this.inputStream.close();
                this.outputStream.close();
                this.clientSocket.close();
                System.out.println("Connection closed\r\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void initializeConnection(Socket clientSocket) {
        try {
            this.clientSocket = clientSocket;
            this.inputStream = clientSocket.getInputStream();
            this.outputStream = clientSocket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

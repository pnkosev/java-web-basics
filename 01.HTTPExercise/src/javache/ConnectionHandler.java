package javache;

import javache.io.Reader;
import javache.io.Writer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ConnectionHandler extends Thread {
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
            String requestContent = null;
            while (true) {
                requestContent = Reader.readAllLines(this.inputStream);

                if (requestContent.length() > 0) {
                    break;
                }
            }
            byte[] byteData = this.requestHandler.handleRequest(requestContent);
            Writer.writeBytes(byteData, this.outputStream);
            this.inputStream.close();
            this.outputStream.close();
            this.clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
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

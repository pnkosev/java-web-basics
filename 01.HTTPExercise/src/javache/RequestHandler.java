package javache;

import javache.http.HttpRequest;
import javache.http.impl.HttpRequestImpl;

public class RequestHandler {

    public byte[] handleRequest(String request) {

        HttpRequest httpRequest = new HttpRequestImpl(
                "POST /url HTTP/1.1\r\n" +
                        "Date: 17/01/2019\r\n" +
                        "Host: localhost:8000\r\n" +
                        "Content-Type: application/xml\r\n" +
                        "Authorization: Basic UGVzaG8=\r\n" +
                        "<CRLF>\r\n" +
                        "name=Yum&quantity=50&price=10"
        );

        StringBuilder sb = new StringBuilder();

        sb.append("HTTP 1.1 200 OK").append(System.lineSeparator());
        sb.append("Content-Type: text/html").append(System.lineSeparator());
        sb.append(System.lineSeparator());
        sb.append("<h1>Hello, World!</h1>").append(System.lineSeparator());

        return sb.toString().getBytes();
    }
}

package javache;

import javache.http.HttpRequest;
import javache.http.impl.HttpRequestImpl;

public class RequestHandler {

    public byte[] handleRequest(String request) {

        HttpRequest httpRequest = new HttpRequestImpl(
                "POST /url HTTP/1.1\n" +
                        "Date: 17/01/2019\n" +
                        "Host: localhost:8000\n" +
                        "Content-Type: application/xml\n" +
                        "Authorization: Basic UGVzaG8=\n" +
                        "name=Yum&amp;quantity=50&amp;price=10"
        );

        StringBuilder sb = new StringBuilder();

        sb.append("HTTP 1.1 200 OK").append(System.lineSeparator());
        sb.append("Content-Type: text/html").append(System.lineSeparator());
        sb.append(System.lineSeparator());
        sb.append("<h1>Hello, World!</h1>").append(System.lineSeparator());

        return sb.toString().getBytes();
    }
}

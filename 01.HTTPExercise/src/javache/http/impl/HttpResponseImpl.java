package javache.http.impl;

import javache.http.HttpResponse;

import java.util.HashMap;

public class HttpResponseImpl implements HttpResponse {
    private HashMap<String, String> headers;
    private int statusCode;
    private byte[] content;

    public HttpResponseImpl() {
        this.headers = new HashMap<>();
        this.setContent(new byte[0]);
    }


    @Override
    public HashMap<String, String> getHeaders() {
        return this.headers;
    }

    @Override
    public int getStatusCode() {
        return this.statusCode;
    }

    @Override
    public byte[] getContent() {
        return this.content;
    }

    @Override
    public byte[] getBytes() {
        return new byte[0];
    }

    @Override
    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    public void setContent(byte[] content) {
        this.content = content;
    }

    @Override
    public void addHeader(String header, String value) {
        this.headers.putIfAbsent(header, value);
    }
}

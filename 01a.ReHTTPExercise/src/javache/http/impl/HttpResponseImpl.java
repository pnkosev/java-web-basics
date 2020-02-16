package javache.http.impl;

import javache.WebConstants;
import javache.http.HttpResponse;
import javache.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

public class HttpResponseImpl implements HttpResponse {
    private HashMap<String, String> headers;
    private HttpStatus statusCode;
    private byte[] content;

    public HttpResponseImpl() {
        this.headers = new HashMap<>();
        this.setContent(new byte[0]);
    }

    private byte[] getHeadersBytes() {
        StringBuilder sb = new StringBuilder();

        sb.append(WebConstants.SERVER_HTTP_VERSION).append(this.statusCode).append(System.lineSeparator());

        for (Map.Entry<String, String> entry : headers.entrySet()) {
            sb.append(entry.getKey()).append(": ").append(entry.getValue()).append(System.lineSeparator());
        }

        sb.append(System.lineSeparator());

        return sb.toString().getBytes();
    }

    @Override
    public byte[] getBytes() {
        byte[] headersBytes = this.getHeadersBytes();
        byte[] content = this.getContent();

        byte[] fullResponse = new byte[headersBytes.length + content.length];

        System.arraycopy(headersBytes, 0, fullResponse, 0, headersBytes.length);
        System.arraycopy(content, 0, fullResponse, headersBytes.length, content.length);

        return fullResponse;
    }

    @Override
    public HashMap<String, String> getHeaders() {
        return this.headers;
    }

    @Override
    public HttpStatus getStatusCode() {
        return this.statusCode;
    }

    @Override
    public byte[] getContent() {
        return this.content;
    }

    @Override
    public void setStatusCode(HttpStatus statusCode) {
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

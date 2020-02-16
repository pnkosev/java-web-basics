package javache.http.impl;

import javache.WebConstants;
import javache.http.HttpCookie;
import javache.http.HttpResponse;
import javache.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

public class HttpResponseImpl implements HttpResponse {
    private HashMap<String, String> headers;
    private HashMap<String, HttpCookie> cookies;
    private HttpStatus statusCode;
    private byte[] content;

    public HttpResponseImpl() {
        this.setContent(new byte[0]);
        this.headers = new HashMap<>();
        this.cookies = new HashMap<>();
    }

    private byte[] getHeadersBytes() {
        StringBuilder sb = new StringBuilder();

        sb.append(WebConstants.SERVER_HTTP_VERSION).append(" ").append(this.statusCode.getStatusPhrase()).append(System.lineSeparator());

        for (Map.Entry<String, String> entry : headers.entrySet()) {
            sb.append(entry.getKey()).append(": ").append(entry.getValue()).append(System.lineSeparator());
        }

        if (!this.cookies.isEmpty()) {
            sb.append("Set-Cookie: ");

            for (HttpCookie cookie : cookies.values()) {
                sb.append(cookie.toString());
            }

            // Deleting the last "; "
            sb.replace(sb.length() - 2, sb.length(), "");
            sb.append(System.lineSeparator());
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

    @Override
    public void addCookie(String name, String value) {
        this.cookies.putIfAbsent(name, new HttpCookieImpl(name, value));
    }
}

package javache.http.impl;

import javache.http.HttpCookie;
import javache.http.HttpRequest;

import java.util.HashMap;

public class HttpRequestImpl implements HttpRequest {
    private HashMap<String, String> headers;
    private HashMap<String, String> bodyParams;
    private HashMap<String, HttpCookie> cookies;
    private String method;
    private String requestUrl;

    public HttpRequestImpl(String requestContent) {
        this.initialize(requestContent);
    }

    private void initialize(String requestContent) {
        this.initializeMethod(requestContent);
        this.initializeRequestUrl(requestContent);
        this.initializeHeaders(requestContent);
        this.initializeBodyParams(requestContent);
        this.initializeCookies();
    }

    private void initializeMethod(String requestContent) {
        this.setMethod(requestContent.split("\\s")[0]);
    }

    private void initializeRequestUrl(String requestContent) {
        this.setRequestUrl(requestContent.split("\\s")[1]);
    }

    private void initializeHeaders(String requestContent) {
        this.headers = new HashMap<>();

        String[] requestLines = requestContent.split("\\r\\n"); // "\\r\\n"

        for (int i = 1; i < requestLines.length; i++) {
            String currentLine = requestLines[i];
            String[] header = currentLine.split("\\:\\s");
            if (header.length != 1) {
                this.addHeader(header[0], header[1]);
            }
        }
    }

    private void initializeBodyParams(String requestContent) {
        if (this.method.equals("POST")) {
            this.bodyParams = new HashMap<>();

            String[] requestParams = requestContent.split("\\r\\n");

            if (requestParams.length - this.headers.size() > 2) {
                String[] params = requestParams[requestParams.length - 1].split("\\&");
                for (String s : params) {
                    String[] param = s.split("=");
                    this.addBodyParameter(param[0], param[1]);
                }
            }
        }
    }

    private void initializeCookies() {
        this.cookies = new HashMap<>();

        if (!this.headers.containsKey("Cookie")) {
            return;
        }

        String[] allCookies = this.headers.get("Cookie").split(";\\s");

        for (String cookie : allCookies) {
            String[] cookieKeyValuePair = cookie.split("=");

            this.cookies.putIfAbsent(cookieKeyValuePair[0], new HttpCookieImpl(cookieKeyValuePair[0], cookieKeyValuePair[1]));
        }
    }

    @Override
    public HashMap<String, String> getHeaders() {
        return this.headers;
    }

    @Override
    public HashMap<String, String> getBodyParameters() {
        return this.bodyParams;
    }

    @Override
    public HashMap<String, HttpCookie> getCookies() { return this.cookies; }

    @Override
    public String getMethod() {
        return this.method;
    }

    @Override
    public void setMethod(String method) {
        this.method = method;
    }

    @Override
    public String getRequestUrl() {
        return this.requestUrl;
    }

    @Override
    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    @Override
    public void addHeader(String header, String value) {
        this.headers.putIfAbsent(header, value);
    }

    @Override
    public void addBodyParameter(String parameter, String value) {
        this.bodyParams.putIfAbsent(parameter, value);
    }

    @Override
    public boolean isResource() {
        return this.requestUrl.contains(".");
    }
}

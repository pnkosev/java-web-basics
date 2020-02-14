package javache.http.impl;

import javache.http.HttpRequest;

import java.util.HashMap;

public class HttpRequestImpl implements HttpRequest {
    private HashMap<String, String> headers;
    private HashMap<String, String> bodyParams;
    private String method;
    private String requestUrl;

    public HttpRequestImpl(String requestContent) {
        this.initialize(requestContent);
    }

    private void initialize(String requestContent) {
        this.initializeMethod(requestContent);
        this.initializeRequestUrl(requestContent);
    }

    private void initializeMethod(String requestContent) {
        this.setMethod(requestContent.split("\\s")[0]);
    }

    private void initializeRequestUrl(String requestContent) {
        this.setRequestUrl(requestContent.split("\\s")[1]);
    }

    @Override
    public HashMap<String, String> getHeaders() {
        return null;
    }

    @Override
    public HashMap<String, String> getBodyParameters() {
        return null;
    }

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

    }

    @Override
    public void addBodyParameter(String parameter, String value) {

    }

    @Override
    public boolean isResource() {
        return this.requestUrl.contains(".");
    }
}

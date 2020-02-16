package javache;

import javache.http.HttpRequest;
import javache.http.HttpResponse;
import javache.http.HttpStatus;
import javache.http.impl.HttpRequestImpl;
import javache.http.impl.HttpResponseImpl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class RequestHandler {

    private static final String HTML_EXTENSION = ".html";
    private HttpRequest request;
    private HttpResponse response;

    public byte[] handleRequest(String requestContent) {
        this.request = new HttpRequestImpl(requestContent);
        this.response = new HttpResponseImpl();
        String method = this.request.getMethod();

        byte[] result = null;

        if (method.equalsIgnoreCase("get")) {
            result = this.processGetRequest();
        } else if (method.equalsIgnoreCase("post")) {
            return new byte[0];
        }

        return result;
    }

    private byte[] ok(byte[] result) {
        this.response.setStatusCode(HttpStatus.Ok);
        this.response.setContent(result);

        return this.response.getBytes();
    }

    private byte[] notFound(byte[] result) {
        this.response.setStatusCode(HttpStatus.NotFound);
        this.response.setContent(result);

        return this.response.getBytes();
    }

    private byte[] internalServerError(byte[] result) {
        this.response.setStatusCode(HttpStatus.InternalServerError);
        this.response.setContent(result);

        return this.response.getBytes();
    }

    private byte[] processGetRequest() {
        String requestUrl = this.request.getRequestUrl();

        if (this.request.isResource()) {
            return this.processResourceRequest();
        } else {
            if (requestUrl.equals("/")) {
                return this.processPageRequest("index");
            } else {
                return this.processPageRequest(requestUrl);
            }
        }
    }

    private byte[] processPageRequest(String page) {
        String path = WebConstants.RESOURCE_PAGE_PATH + page + HTML_EXTENSION;

        File file = new File(path);

        if (!file.exists() || file.isDirectory()) {
            return this.notFound(("Asset not found").getBytes());
        }

        byte[] result = null;

        try {
            result = Files.readAllBytes(Paths.get(path));
        } catch (IOException e) {
            return this.internalServerError(("Something went wrong").getBytes());
        }

        this.response.addHeader("Content-Type", this.getMimeType(file));

        return this.ok(result);
    }

    private byte[] processResourceRequest() {
        String requestUrl = this.request.getRequestUrl();

        if (requestUrl.startsWith("/assets")) {
            requestUrl = requestUrl.split("/")[2];
        }

        String path = WebConstants.RESOURCE_ASSET_PATH + requestUrl;

        File file = new File(path);

        if (!file.exists() || file.isDirectory()) {
            return this.notFound(("Page not found").getBytes());
        }

        byte[] result = null;

        try {
            result = Files.readAllBytes(Paths.get(path));
        } catch (IOException e) {
            return this.internalServerError(("Something went wrong").getBytes());
        }

        this.response.addHeader("Content-Type", this.getMimeType(file));

        return this.ok(result);
    }

    private String getMimeType(File file) {
        String fileName = file.getName();

        if (fileName.endsWith("html")) {
            return "text/html";
        } else if (fileName.endsWith("css")) {
            return "text/css";
        } else if (fileName.endsWith("jpg") || fileName.endsWith("jpeg")) {
            return "image/jpeg";
        } else if (fileName.endsWith("png")) {
            return "image/png";
        } else if (fileName.endsWith("ico")) {
            return "image/x-icon";
        }

        return "text/plain";
    }
}

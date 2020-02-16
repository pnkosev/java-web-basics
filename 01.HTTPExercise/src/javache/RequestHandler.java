package javache;

import javache.http.*;
import javache.http.impl.HttpRequestImpl;
import javache.http.impl.HttpResponseImpl;
import javache.http.impl.HttpSessionImpl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class RequestHandler {

    private static final String HTML_EXTENSION = ".html";

    private HttpRequest request;
    private HttpResponse response;
    private HttpSessionStorage sessionStorage;

    public RequestHandler(HttpSessionStorage sessionStorage) {
        this.sessionStorage = sessionStorage;
    }

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

        this.sessionStorage.refreshSessions();

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

    private byte[] redirect(byte[] result, String location) {
        this.response.setStatusCode(HttpStatus.SeeOther);
        this.response.addHeader("Location", location);
        this.response.setContent(result);

        return this.response.getBytes();
    }

    private byte[] internalServerError(byte[] result) {
        this.response.setStatusCode(HttpStatus.InternalServerError);
        this.response.setContent(result);

        return this.response.getBytes();
    }

    private byte[] processGetRequest() {
        if (this.request.isResource()) {
            return this.processResourceRequest();
        } else {
            switch (this.request.getRequestUrl()) {
                case "/":
                    return this.processPageRequest("index");
                case "/about":
                    return this.processPageRequest("about");
                case "/register":
                    return this.processPageRequest("register");
                case "/login":
                    HttpSession session = new HttpSessionImpl();
                    session.addAttribute("username", "pesho");

                    this.sessionStorage.addSession(session);

                    this.response.addCookie("Javache", session.getId());

                    return this.processPageRequest("login");
                case "/forbidden":
                    if (!this.request.getCookies().containsKey("Javache")) {
                        return this.redirect(("You have to be connected to be able to access this route").getBytes(), "/");
                    }

                    String sessionId = this.request.getCookies().get("Javache").getValue();
                    HttpSession currentSession = this.sessionStorage.getById(sessionId);
                    String username = currentSession.getAttributes().get("username").toString();

                    return this.ok(("<h1>Hello, " + username + "</h1>").getBytes());
                case "/logout":
                    if (!this.request.getCookies().containsKey("Javache")) {
                        return this.redirect(("You have to be connected to be able to logout").getBytes(), "/");
                    }

                    this.response.addCookie("Javache", "deleted; expires=Thu, 01 Jan 1970 00:00:00 GMT;");
                    return this.redirect(("Hope to see you soon").getBytes(), "/");
                default:
                    return this.notFound(("Page not Found").getBytes());
            }
        }
    }

    private byte[] processPageRequest(String page) {
        String path = WebConstants.RESOURCE_PAGE_PATH + page + HTML_EXTENSION;

        File file = new File(path);

        if (!file.exists() || file.isDirectory()) {
            return this.notFound(("Page not found").getBytes());
        }

        byte[] result = this.getBytesFromFile(path);

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
            return this.notFound(("Asset not found").getBytes());
        }

        byte[] result = this.getBytesFromFile(path);

        this.response.addHeader("Content-Type", this.getMimeType(file));

        return this.ok(result);
    }

    private byte[] getBytesFromFile(String path) {
        byte[] result = null;

        try {
            result = Files.readAllBytes(Paths.get(path));
        } catch (IOException e) {
            return this.internalServerError(("Something went wrong").getBytes());
        }

        return result;
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

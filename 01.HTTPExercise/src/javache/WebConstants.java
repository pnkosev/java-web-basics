package javache;

public final class WebConstants {
    public final static int SERVER_PORT = 8001;
    public final static String SERVER_HTTP_VERSION = "HTTP/1.1";
    public final static String RESOURCE_FOLDER_PATH = System.getProperty("user.dir") + "\\src\\resources\\";
    public final static String RESOURCE_PAGE_PATH = RESOURCE_FOLDER_PATH + "pages\\";
    public final static String RESOURCE_ASSET_PATH = RESOURCE_FOLDER_PATH + "assets\\";

    private WebConstants() {}
}

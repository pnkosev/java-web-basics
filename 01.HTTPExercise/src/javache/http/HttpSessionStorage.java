package javache.http;

public interface HttpSessionStorage {
    HttpSession getById(String id);

    void addSession(HttpSession session);

    void refreshSessions();
}

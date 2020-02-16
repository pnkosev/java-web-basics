package javache.http.impl;

import javache.http.HttpSession;
import javache.http.HttpSessionStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HttpSessionStorageImpl implements HttpSessionStorage {
    private HashMap<String, HttpSession> sessions;

    public HttpSessionStorageImpl() {
        this.sessions = new HashMap<>();
    }

    @Override
    public HttpSession getById(String id) {
        if (!this.sessions.containsKey(id)) {
            return null;
        }

        return this.sessions.get(id);
    }

    @Override
    public void addSession(HttpSession session) {
        this.sessions.putIfAbsent(session.getId(), session);
    }

    @Override
    public void refreshSessions() {
        List<String> invalidSessionsIds = new ArrayList<>();

        for (HttpSession session : sessions.values()) {
            if (!session.isValid()) {
                invalidSessionsIds.add(session.getId());
            }
        }

        for (String invalidSessionId : invalidSessionsIds) {
            this.sessions.remove(invalidSessionId);
        }
    }
}

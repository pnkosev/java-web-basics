package web;

import javax.faces.context.FacesContext;
import java.io.IOException;

public class BaseBean {

    protected BaseBean() { }

    protected void redirect(String url) {
        try {
            FacesContext.getCurrentInstance()
                    .getExternalContext()
                    .redirect(String.format("/vies%s.jsf", url));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected <V> void addIntoSession(String key, V value) {
        FacesContext.getCurrentInstance()
                .getExternalContext()
                .getSessionMap()
                .put(key, value);
    }

    protected String getParamFromQuery(String paramName) {
        return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(paramName);
    }
}

package web.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter({
        "/views/home.jsf",
        "/views/friends.jsf",
        "/views/profile.jsf"
})
public class NonAuthorizedUsersFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String username = (String) ((HttpServletRequest) servletRequest).getSession().getAttribute("username");

        if (username == null) {
            ((HttpServletResponse) servletResponse).sendRedirect("/views/login.jsf");
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}

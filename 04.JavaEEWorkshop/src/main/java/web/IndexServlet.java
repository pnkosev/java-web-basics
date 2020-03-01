package web;

import domain.entity.User;
import repository.Test;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/index")
public class IndexServlet extends HttpServlet {
    private static int i = 1;

    private final Test test;

    @Inject
    public IndexServlet(Test test) {
        this.test = test;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(i++);

        List<User> all = this.test.findAll();

        System.out.println(all);

        req.getRequestDispatcher("index.jsp")
                .forward(req, resp);
    }
}

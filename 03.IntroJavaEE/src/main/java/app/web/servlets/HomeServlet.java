package app.web.servlets;

import app.util.FileUtil;

import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/")
public class HomeServlet extends HttpServlet {

    private final static String HOME_PAGE_PATH = "C:\\Users\\CDA1\\Desktop\\Projets\\Java\\Java-Web\\03.IntroJavaEE\\src\\main\\webapp\\views\\home.html";

    private final FileUtil fileUtil;

    @Inject
    public HomeServlet(FileUtil fileUtil) {
        this.fileUtil = fileUtil;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String homeHTML = this.fileUtil.readFile(HOME_PAGE_PATH);
        PrintWriter out = res.getWriter();

        out.println(homeHTML);
    }
}

package web;

import domain.model.binding.UserLoginBindingModel;
import domain.model.service.UserServiceModel;
import org.modelmapper.ModelMapper;
import service.api.UserService;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/users/login")
public class UserLoginServlet extends HttpServlet {

    private final UserService userService;

    private final ModelMapper modelMapper;

    @Inject
    public UserLoginServlet(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/user-login.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserLoginBindingModel userLoginBindingModel = new UserLoginBindingModel();

        userLoginBindingModel.setUsername(req.getParameter("username"));
        userLoginBindingModel.setPassword(req.getParameter("password"));

        if (!this.userService.loginUser(this.modelMapper.map(userLoginBindingModel, UserServiceModel.class))) {
            req.getRequestDispatcher("/user-login.jsp")
                    .forward(req, resp);

            return;
        }

        req.getSession().setAttribute("username", userLoginBindingModel.getUsername());

        resp.sendRedirect("/home.jsp");
    }
}

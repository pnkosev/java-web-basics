package web;

import domain.model.binding.UserRegisterBindingModel;
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

@WebServlet("/users/register")
public class UserRegisterServlet extends HttpServlet {

    private final UserService userService;

    private final ModelMapper modelMapper;

    @Inject
    public UserRegisterServlet(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/jsp/user-register.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserRegisterBindingModel userRegisterBindingModel = new UserRegisterBindingModel();
        userRegisterBindingModel.setUsername(req.getParameter("username"));
        userRegisterBindingModel.setPassword(req.getParameter("password"));
        userRegisterBindingModel.setConfirmPassword(req.getParameter("confirmPassword"));
        userRegisterBindingModel.setEmail(req.getParameter("email"));

        if (!userRegisterBindingModel.getPassword().equals(userRegisterBindingModel.getConfirmPassword())) {
            req.getRequestDispatcher("/jsp/user-register.jsp")
                    .forward(req, resp);
        }

        this.userService.registerUser(this.modelMapper.map(userRegisterBindingModel, UserServiceModel.class));

        resp.sendRedirect("/users/login");
    }
}

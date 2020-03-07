package app.web.beans;

import app.domain.model.binding.UserLoginBindingModel;
import app.domain.model.service.UserServiceModel;
import app.service.UserService;
import org.hibernate.TransactionException;
import org.modelmapper.ModelMapper;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class UserLoginBean extends BaseBean {

    private UserService userService;

    private ModelMapper modelMapper;

    private UserLoginBindingModel user;

    public UserLoginBean() {
    }

    @Inject
    public UserLoginBean(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PostConstruct
    private void init() {
        this.user = new UserLoginBindingModel();
    }

    public void login() {
        UserServiceModel userServiceModel = null;

        try {
            userServiceModel = this.userService.findUser(this.modelMapper.map(user, UserServiceModel.class));
        } catch (TransactionException e) {
            e.printStackTrace();
            redirect("/login");
            return;
        }

        this.addIntoSession("username", userServiceModel.getUsername());

        this.redirect("/home");
    }

    public UserLoginBindingModel getUser() {
        return this.user;
    }

    public void setUser(UserLoginBindingModel user) {
        this.user = user;
    }
}

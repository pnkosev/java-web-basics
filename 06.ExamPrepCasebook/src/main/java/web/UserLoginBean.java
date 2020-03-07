package web;

import domain.models.binding.UserLoginBindingModel;
import domain.models.service.UserServiceModel;
import org.modelmapper.ModelMapper;
import service.UserService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class UserLoginBean extends BaseBean {

    private UserService userService;

    private UserLoginBindingModel user;

    public UserLoginBean() { }

    @Inject
    public UserLoginBean(UserService userService) {
        this.userService = userService;
    }

    public void login() {
        UserServiceModel userServiceModel = this.userService.getByUsernameAndPassword(this.user.getUsername(), this.user.getPassword());

        if (userServiceModel != null) {
            this.redirect("/home");
            this.addIntoSession("username", userServiceModel.getUsername());
        } else {
            redirect("/login");
        }
    }

    public UserLoginBindingModel getUser() {
        return user;
    }

    public void setUser(UserLoginBindingModel user) {
        this.user = user;
    }
}

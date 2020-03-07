package web;

import domain.models.binding.UserRegisterBindingModel;
import domain.models.service.UserServiceModel;
import org.modelmapper.ModelMapper;
import service.UserService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class UserRegisterBean extends BaseBean {

    private UserService userService;

    private ModelMapper modelMapper;

    private UserRegisterBindingModel user;

    public UserRegisterBean() { }

    @Inject
    public UserRegisterBean(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    public void register() {
        if (!this.user.getPassword().equals(this.user.getConfirmPassword())) {
            this.redirect("/register");
            return;
        }

        this.userService.create(this.modelMapper.map(this.user, UserServiceModel.class));

        this.redirect("/login");
    }

    public UserRegisterBindingModel getUser() {
        return user;
    }

    public void setUser(UserRegisterBindingModel user) {
        this.user = user;
    }
}

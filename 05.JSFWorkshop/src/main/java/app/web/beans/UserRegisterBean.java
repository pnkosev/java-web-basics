package app.web.beans;

import app.domain.model.binding.UserRegisterBindingModel;
import app.domain.model.service.UserServiceModel;
import app.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.TransactionException;
import org.modelmapper.ModelMapper;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class UserRegisterBean extends BaseBean {

    private UserService userService;

    private ModelMapper modelMapper;

    private UserRegisterBindingModel user;

    public UserRegisterBean() {
    }

    @Inject
    public UserRegisterBean(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PostConstruct
    private void init() {
        this.user = new UserRegisterBindingModel();
    }

    public void register() {
        if (this.user.getPassword().equals(this.user.getConfirmPassword())) {
            try {

                this.user.setPassword(DigestUtils.sha256Hex(this.user.getPassword()));

                this.userService
                        .save(this.modelMapper.map(this.user, UserServiceModel.class));

            } catch (TransactionException e) {
                e.printStackTrace();
                this.redirect("/register");
                return;
            }

            this.redirect("/login");
        }
    }

    public UserRegisterBindingModel getUser() {
        return this.user;
    }

    public void setUser(UserRegisterBindingModel user) {
        this.user = user;
    }
}

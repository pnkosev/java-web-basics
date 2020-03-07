package web;

import domain.models.view.UserProfileViewModel;
import org.modelmapper.ModelMapper;
import service.UserService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class ProfileBean extends BaseBean {

    private UserService userService;

    private ModelMapper modelMapper;

    private UserProfileViewModel profile;

    public ProfileBean() { }

    @Inject
    public ProfileBean(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PostConstruct
    private void init() {
        this.profile = this.modelMapper.map(this.userService.getById(this.getParamFromQuery("id")), UserProfileViewModel.class);
    }

    public UserProfileViewModel getProfile() {
        return profile;
    }

    public void setProfile(UserProfileViewModel profile) {
        this.profile = profile;
    }
}

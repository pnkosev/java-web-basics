package web;

import domain.models.service.UserServiceModel;
import domain.models.view.UserViewModel;
import org.modelmapper.ModelMapper;
import service.UserService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.stream.Collectors;

@Named
@RequestScoped
public class HomeBean extends BaseBean {

    private UserService userService;

    private ModelMapper modelMapper;

    private List<UserViewModel> users;

    public HomeBean() {
    }

    @Inject
    public HomeBean(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PostConstruct
    private void init() {
        String username = this.getParamFromSession("username");

        this.setUsers(this.userService.getAll()
                .stream()
                .filter(u -> !u.getUsername().equals(username) &&
                        !u.getFriends()
                                .stream()
                                .map(UserServiceModel::getUsername)
                                .collect(Collectors.toList())
                                .contains(username))
                .map(u -> this.modelMapper.map(u, UserViewModel.class))
                .collect(Collectors.toList()));
    }

    public void addFriend(String targetFriendId) {
        String userId = this.getParamFromSession("userId");

        this.userService.addFriend(userId, targetFriendId);

        this.redirect("/home");
    }

    public List<UserViewModel> getUsers() {
        return users;
    }

    public void setUsers(List<UserViewModel> users) {
        this.users = users;
    }
}

package web;

import domain.models.view.UserFriendsViewModel;
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
public class UserFriendsBean extends BaseBean {

    private UserService userService;

    private ModelMapper modelMapper;

    private List<UserFriendsViewModel> friends;

    public UserFriendsBean() {
    }

    @Inject
    public UserFriendsBean(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PostConstruct
    private void init() {
        String id = this.getParamFromSession("userId");

        friends = this.userService.getById(id).getFriends()
                .stream()
                .map(f -> this.modelMapper.map(f, UserFriendsViewModel.class))
                .collect(Collectors.toList());
    }

    public void removeFriend(String friendId) {
        String userId = this.getParamFromSession("userId");
        this.userService.removeFriend(userId, friendId);

        this.redirect("/friends");
    }

    public List<UserFriendsViewModel> getFriends() {
        return friends;
    }

    public void setFriends(List<UserFriendsViewModel> friends) {
        this.friends = friends;
    }
}

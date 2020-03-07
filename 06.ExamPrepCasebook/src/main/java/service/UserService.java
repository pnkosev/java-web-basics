package service;

import domain.models.service.UserServiceModel;

import java.util.List;

public interface UserService {

    void create(UserServiceModel userServiceModel);

    void update(UserServiceModel userServiceModel);

    UserServiceModel getById(String id);

    UserServiceModel getByUsernameAndPassword(String username, String password);

    List<UserServiceModel> getAll();

    void addFriend(String userId, String friendId);

    void removeFriend(String userId, String friendId);
}

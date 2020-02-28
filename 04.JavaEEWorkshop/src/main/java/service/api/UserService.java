package service.api;

import domain.model.service.UserServiceModel;

public interface UserService {
    boolean registerUser(UserServiceModel user);

    boolean loginUser(UserServiceModel user);

    UserServiceModel findUserByUsername(String name);
}

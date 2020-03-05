package app.service;

import app.domain.model.service.UserServiceModel;

public interface UserService {

    void save(UserServiceModel user);

    UserServiceModel findUser(UserServiceModel user);
}

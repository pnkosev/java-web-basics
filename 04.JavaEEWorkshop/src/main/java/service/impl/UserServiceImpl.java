package service.impl;

import domain.entity.User;
import domain.model.service.UserServiceModel;
import org.apache.commons.codec.digest.DigestUtils;
import org.modelmapper.ModelMapper;
import repository.api.UserRepository;
import service.api.UserService;

import javax.inject.Inject;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    @Inject
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean registerUser(UserServiceModel userServiceModel) {
        User user = this.modelMapper.map(userServiceModel, User.class);

        user.setPassword(DigestUtils.sha256Hex(userServiceModel.getPassword()));

        try {
            this.userRepository.save(user);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean loginUser(UserServiceModel user) {
        return this.userRepository
                .findByUsernameAndPassword(
                        user.getUsername(), DigestUtils.sha256Hex(user.getPassword())) != null;
    }

    @Override
    public UserServiceModel findUserByUsername(String name) {
        return this.modelMapper
                .map(this.userRepository
                        .findByUsername(name), UserServiceModel.class);
    }
}

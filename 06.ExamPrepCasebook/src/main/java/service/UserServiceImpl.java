package service;

import domain.entities.Gender;
import domain.entities.User;
import domain.models.service.UserServiceModel;
import org.apache.commons.codec.digest.DigestUtils;
import org.modelmapper.ModelMapper;
import repository.UserRepository;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    @Inject
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void create(UserServiceModel userServiceModel) {
        User user = this.modelMapper.map(userServiceModel, User.class);
        user.setPassword(DigestUtils.sha256Hex(userServiceModel.getPassword()));
        user.setGender(stringToGender(userServiceModel.getGender()));
        this.userRepository.save(user);
    }

    @Override
    public void update(UserServiceModel userServiceModel) {
        User user = this.modelMapper.map(userServiceModel, User.class);
        user.setGender(stringToGender(userServiceModel.getGender()));
        this.userRepository.update(user);
    }

    @Override
    public UserServiceModel getById(String id) {
        return this.modelMapper.map(this.userRepository.findById(id), UserServiceModel.class);
    }

    @Override
    public UserServiceModel getByUsernameAndPassword(String username, String password) {
        return this.modelMapper.map(this.userRepository.findByUsernameAndPassword(username, DigestUtils.sha256Hex(password)), UserServiceModel.class);
    }

    @Override
    public List<UserServiceModel> getAll() {
        return this.userRepository.findAll()
                .stream()
                .map(u -> this.modelMapper.map(u, UserServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public void addFriend(String userId, String friendId) {
        User user = this.userRepository.findById(userId);
        User friend = this.userRepository.findById(friendId);

        user.getFriends().add(friend);
        friend.getFriends().add(user);

        this.userRepository.update(user);
        this.userRepository.update(friend);
    }

    @Override
    public void removeFriend(String userId, String friendId) {
        User user = this.userRepository.findById(userId);
        User friend = this.userRepository.findById(friendId);

        user.getFriends().remove(friend);
        friend.getFriends().remove(user);

        this.userRepository.update(user);
        this.userRepository.update(friend);
    }

    private Gender stringToGender(String gender) {
        return Gender.valueOf(gender.toUpperCase());
    }
}

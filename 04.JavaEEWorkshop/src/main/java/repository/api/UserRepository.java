package repository.api;

import domain.entity.User;

public interface UserRepository extends BaseRepository<User, Integer> {
    User findByUsernameAndPassword(String username, String password);

    User findByUsername(String username);
}

package repository.impl;

import domain.entity.User;
import repository.api.UserRepository;
import util.TransactionWrapperUtil;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public class UserRepositoryImpl extends BaseRepositoryImpl<User, Integer> implements UserRepository {

    @Inject
    public UserRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public User findByUsernameAndPassword(String username, String password) {
        return TransactionWrapperUtil
                .getSingleResultInTransaction(
                        this.entityManager, () -> this.entityManager
                                .createQuery(
                                        "SELECT u " +
                                                "FROM User AS u " +
                                                "WHERE u.username = :username AND u.password = :password", User.class)
                                .setParameter("username", username)
                                .setParameter("password", password).getSingleResult());
    }

    @Override
    public User findByUsername(String username) {
        return TransactionWrapperUtil
                .getSingleResultInTransaction(this.entityManager, () -> this.entityManager
                        .createQuery("SELECT u FROM User AS u WHERE u.username = :username", User.class)
                        .setParameter("username", username)
                        .getSingleResult());
    }
}

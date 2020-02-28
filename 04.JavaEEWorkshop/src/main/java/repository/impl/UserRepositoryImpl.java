package repository.impl;

import domain.entity.User;
import repository.api.UserRepository;
import util.TransactionWrapper;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {

    private final EntityManager entityManager;

    @Inject
    public UserRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public User findByUsernameAndPassword(String username, String password) {
        return TransactionWrapper
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
        return TransactionWrapper
                .getSingleResultInTransaction(this.entityManager, () -> this.entityManager
                        .createQuery("SELECT u FROM User AS u WHERE u.username = :username", User.class)
                        .setParameter("username", username)
                        .getSingleResult());
    }

    @Override
    public void save(User user) {
        TransactionWrapper.persistInTransaction(this.entityManager, () -> this.entityManager.persist(user));
    }

    @Override
    public void merge(User user) {
        TransactionWrapper.persistInTransaction(this.entityManager, () -> this.entityManager.merge(user));
    }

    @Override
    public List<User> findAll() {
        return TransactionWrapper
                .getListInTransaction(this.entityManager, () -> this.entityManager
                        .createQuery("SELECT u FROM User AS u", User.class)
                        .getResultList());
    }

    @Override
    public User findById(Integer id) {
        return TransactionWrapper
                .getSingleResultInTransaction(this.entityManager, () -> this.entityManager
                        .createQuery("SELECT u FROM User AS u WHERE u.id = :id", User.class)
                        .setParameter("id", id)
                        .getSingleResult());
    }

    @Override
    public long size() {
        return TransactionWrapper
                .getSingleResultInTransaction(this.entityManager, () -> this.entityManager
                        .createQuery("SELECT count(u) FROM User AS u", long.class)
                        .getSingleResult());
    }
}

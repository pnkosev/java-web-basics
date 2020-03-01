package repository;

import domain.entity.User;
import repository.impl.BaseRepositoryImpl;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public class TestImpl extends BaseRepositoryImpl<User, Integer> implements Test {

    @Inject
    protected TestImpl(EntityManager entityManager) {
        super(entityManager);
    }
}

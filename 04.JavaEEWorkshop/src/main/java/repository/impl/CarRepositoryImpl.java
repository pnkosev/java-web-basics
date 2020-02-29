package repository.impl;

import domain.entity.Car;
import repository.api.CarRepository;
import util.TransactionWrapper;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

public class CarRepositoryImpl implements CarRepository {

    private final EntityManager entityManager;

    @Inject
    public CarRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Car update(Car car) {
        return TransactionWrapper
                .getSingleResultInTransaction(this.entityManager, () -> this.entityManager.merge(car));
    }

    @Override
    public void save(Car car) {
        TransactionWrapper
                .persistInTransaction(this.entityManager, () -> this.entityManager.persist(car));
    }

    public void merge(Car car) {
        TransactionWrapper
                .persistInTransaction(this.entityManager, () -> this.entityManager.merge(car));
    }

    @Override
    public List<Car> findAll() {
        return TransactionWrapper
                .getListInTransaction(this.entityManager, () -> this.entityManager
                        .createQuery("SELECT c FROM Car as c", Car.class)
                        .getResultList());
    }

    @Override
    public Car findById(Integer integer) {
        return TransactionWrapper
                .getSingleResultInTransaction(this.entityManager, () -> this.entityManager
                        .createQuery("SELECT c FROM Car as c WHERE c.id = :id", Car.class)
                        .getSingleResult());
    }

    @Override
    public long size() {
        return TransactionWrapper
                .getSingleResultInTransaction(this.entityManager, () -> this.entityManager
                        .createQuery("SELECT count(c) FROM Car as c", long.class)
                        .getSingleResult());
    }
}

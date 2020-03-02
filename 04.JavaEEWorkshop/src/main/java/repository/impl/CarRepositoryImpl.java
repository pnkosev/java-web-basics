package repository.impl;

import domain.entity.Car;
import repository.api.CarRepository;
import util.TransactionWrapperUtil;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public class CarRepositoryImpl extends BaseRepositoryImpl<Car, Integer> implements CarRepository {

    @Inject
    public CarRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Car update(Car car) {
        return TransactionWrapperUtil
                .getSingleResultInTransaction(this.entityManager, () -> this.entityManager.merge(car));
    }
}

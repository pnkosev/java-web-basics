package repository.impl;

import repository.api.BaseRepository;
import util.TransactionWrapper;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public class BaseRepositoryImpl<E, Id> implements BaseRepository<E, Id> {

    private Class<E> clazz;

    private final EntityManager entityManager;

    @Inject
    protected BaseRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.clazz = this.initEntityClass();
    }

    @Override
    public void save(E entity) {
        TransactionWrapper
                .persistInTransaction(this.entityManager, () -> this.entityManager
                        .persist(entity));
    }

    @Override
    public void merge(E entity) {
        TransactionWrapper
                .persistInTransaction(this.entityManager, () -> this.entityManager
                        .merge(entity));
    }

    @Override
    public List<E> findAll() {
        return TransactionWrapper
                .getListInTransaction(this.entityManager, () -> this.entityManager
                        .createQuery(String.format("SELECT e FROM %s AS e", clazz.getSimpleName()), this.clazz)
                        .getResultList());
    }

    @Override
    public E findById(Id i) {
        return null;
    }

    @Override
    public long size() {
        return 0;
    }

    @SuppressWarnings("unchecked")
    private Class<E> initEntityClass() {
        return (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
}

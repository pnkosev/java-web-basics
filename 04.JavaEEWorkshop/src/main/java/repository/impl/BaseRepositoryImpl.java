package repository.impl;

import org.hibernate.TransactionException;
import repository.api.BaseRepository;
import util.TransactionWrapperUtil;
import util.api.RepositoryActionInvoker;
import util.api.RepositoryActionResult;
import util.impl.RepositoryActionResultImpl;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public class BaseRepositoryImpl<E, Id> implements BaseRepository<E, Id> {

    private Class<E> clazz;

    protected final EntityManager entityManager;

    protected BaseRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.clazz = this.initEntityClass();
    }

    @Override
    public void save(E entity) {
        TransactionWrapperUtil
                .persistInTransaction(this.entityManager, () -> this.entityManager
                        .persist(entity));
//        this.execute(repositoryActionResult -> this.entityManager.persist(entity));
    }

    @Override
    public void merge(E entity) {
        TransactionWrapperUtil
                .persistInTransaction(this.entityManager, () -> this.entityManager
                        .merge(entity));
    }

    @Override
    public List<E> findAll() {
        return TransactionWrapperUtil
                .getListInTransaction(this.entityManager, () -> this.entityManager
                        .createQuery(String.format("SELECT e FROM %s AS e", clazz.getSimpleName()), this.clazz)
                        .getResultList());

//        return (List<E>) this.execute(new RepositoryActionInvoker() {
//            @Override
//            public void invoke(RepositoryActionResult repositoryActionResult) {
//                repositoryActionResult.setResult(entityManager
//                        .createQuery(String.format("SELECT e FROM %s AS e", clazz.getSimpleName()), this.clazz)
//                        .getResultList());
//            }
//        }).getResult();
//
//        return (List<E>) this.execute(repositoryActionResult -> {
//            repositoryActionResult.setResult(
//                    this.entityManager
//                            .createQuery(String.format("SELECT e FROM %s AS e", clazz.getSimpleName()), this.clazz)
//                            .getResultList());
//        }).getResult();
    }

    @Override
    public E findById(Id i) {
        return null;
    }

    @Override
    public long size() {
        return 0;
    }

    @Override
    public RepositoryActionResult execute(RepositoryActionInvoker invoker) {
        RepositoryActionResult actionResult = new RepositoryActionResultImpl();

        EntityTransaction transaction = this.entityManager.getTransaction();

        try {
            transaction.begin();

            invoker.invoke(actionResult);

            transaction.commit();
        } catch (TransactionException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }

        return actionResult;
    }

    @SuppressWarnings("unchecked")
    private Class<E> initEntityClass() {
        return (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
}

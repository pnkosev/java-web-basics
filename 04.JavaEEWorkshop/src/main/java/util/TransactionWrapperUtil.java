package util;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class TransactionWrapperUtil {
    public static <T> T getSingleResultInTransaction(EntityManager entityManager, Callable<T> callable) {
        entityManager.getTransaction().begin();
        T result = null;
        try {
            result = callable.call();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Transaction problem!");
            entityManager.getTransaction().rollback();
        }
        return result;
    }

    public static <T> List<T> getListInTransaction(EntityManager entityManager, Callable callable) {
        entityManager.getTransaction().begin();
        List<T> result = new ArrayList<>();
        try {
            result = (List<T>) callable.call();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Transaction problem!");
            entityManager.getTransaction().rollback();
        }
        return result;
    }

    public static void persistInTransaction(EntityManager entityManager, Runnable runnable) {
        entityManager.getTransaction().begin();
        try {
            runnable.run();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Transaction problem!");
            entityManager.getTransaction().rollback();
        }
    }
}

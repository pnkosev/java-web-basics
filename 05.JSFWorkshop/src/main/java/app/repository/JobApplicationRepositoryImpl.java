package app.repository;

import app.domain.entity.JobApplication;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class JobApplicationRepositoryImpl implements JobApplicationRepository {

    private final EntityManager entityManager;

    @Inject
    public JobApplicationRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void save(JobApplication jobApplication) {
        this.entityManager.getTransaction().begin();

        this.entityManager.persist(jobApplication);

        this.entityManager.getTransaction().commit();
    }

    @Override
    public List<JobApplication> findAll() {
        List<JobApplication> jobs;

        this.entityManager.getTransaction().begin();

        jobs = this.entityManager
                .createQuery("SELECT ja FROM JobApplication AS ja", JobApplication.class)
                .getResultList();

        this.entityManager.getTransaction().commit();

        return jobs;
    }

    @Override
    public JobApplication findById(String id) {
        JobApplication ja;

        this.entityManager.getTransaction().begin();

        ja = this.entityManager.createQuery("SELECT ja FROM JobApplication AS ja WHERE ja.id = :id", JobApplication.class)
                .setParameter("id", id)
                .getSingleResult();

        this.entityManager.getTransaction().commit();

        return ja;
    }

    @Override
    public void deleteById(String id) {
        this.entityManager.getTransaction().begin();

        this.entityManager.createQuery("DELETE FROM JobApplication AS ja WHERE ja.id = :id")
                .setParameter("id", id).executeUpdate();

        this.entityManager.getTransaction().commit();
    }
}

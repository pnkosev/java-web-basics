package app.repository;

import app.domain.entity.JobApplication;

import java.util.List;

public interface JobApplicationRepository {
    void save(JobApplication jobApplication);

    List<JobApplication> findAll();

    JobApplication findById(String id);

    void deleteById(String id);
}

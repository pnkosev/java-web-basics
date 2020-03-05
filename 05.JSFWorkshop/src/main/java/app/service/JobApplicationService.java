package app.service;

import app.domain.model.service.JobApplicationServiceModel;

import java.util.List;

public interface JobApplicationService {
    void save(JobApplicationServiceModel jobApplicationServiceModel);

    List<JobApplicationServiceModel> getAll();

    JobApplicationServiceModel geById(String id);

    void deleteById(String id);
}

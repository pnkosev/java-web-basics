package app.service;

import app.domain.entity.JobApplication;
import app.domain.entity.Sector;
import app.domain.model.service.JobApplicationServiceModel;
import app.repository.JobApplicationRepository;
import org.modelmapper.ModelMapper;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

public class JobApplicationServiceImpl implements JobApplicationService {

    private final JobApplicationRepository jobApplicationRepository;
    private final ModelMapper modelMapper;

    @Inject
    public JobApplicationServiceImpl(JobApplicationRepository jobApplicationRepository, ModelMapper modelMapper) {
        this.jobApplicationRepository = jobApplicationRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void save(JobApplicationServiceModel jobApplicationServiceModel) {
        this.jobApplicationRepository.save(this.modelMapper.map(jobApplicationServiceModel, JobApplication.class));
    }

    @Override
    public List<JobApplicationServiceModel> getAll() {
        return this.jobApplicationRepository.findAll()
                .stream()
                .map(j -> this.modelMapper.map(j, JobApplicationServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public JobApplicationServiceModel geById(String id) {
        return this.modelMapper.map(this.jobApplicationRepository.findById(id), JobApplicationServiceModel.class);
    }

    @Override
    public void deleteById(String id) {
        this.jobApplicationRepository.deleteById(id);
    }
}

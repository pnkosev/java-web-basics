package app.web;

import app.domain.model.view.JobViewModel;
import app.service.JobApplicationService;
import org.modelmapper.ModelMapper;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.stream.Collectors;

@Named
@RequestScoped
public class JobListBean {

    private JobApplicationService jobApplicationService;

    private ModelMapper modelMapper;

    private List<JobViewModel> jobs;

    public JobListBean() {
    }

    @Inject
    public JobListBean(JobApplicationService jobApplicationService, ModelMapper modelMapper) {
        this.jobApplicationService = jobApplicationService;
        this.modelMapper = modelMapper;
    }

    @PostConstruct
    private void init() {
        this.jobs = this.jobApplicationService.getAll()
                .stream()
                .map(j -> {
                    JobViewModel jlvm = this.modelMapper.map(j, JobViewModel.class);
                    jlvm.setSector(j.getSector().toString().toLowerCase());
                    return jlvm;
                })
                .collect(Collectors.toList());
    }

    public List<JobViewModel> getJobs() {
        return this.jobs;
    }
}

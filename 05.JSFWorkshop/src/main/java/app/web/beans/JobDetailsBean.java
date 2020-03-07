package app.web.beans;

import app.domain.model.view.JobViewModel;
import app.service.JobApplicationService;
import org.modelmapper.ModelMapper;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class JobDetailsBean extends BaseBean {

    private JobApplicationService jobApplicationService;

    private ModelMapper modelMapper;

    private JobViewModel jobViewModel;

    public JobDetailsBean() {
    }

    @Inject
    public JobDetailsBean(JobApplicationService jobApplicationService, ModelMapper modelMapper) {
        this.jobApplicationService = jobApplicationService;
        this.modelMapper = modelMapper;
    }

    @PostConstruct
    private void init() {
        this.jobViewModel = this.modelMapper.map(this.jobApplicationService.geById(this.getParamFromQuery("id")), JobViewModel.class);
    }

    public JobViewModel getJobViewModel() {
        return this.jobViewModel;
    }

    public void setJobViewModel(JobViewModel jobViewModel) {
        this.jobViewModel = jobViewModel;
    }
}

package app.web.beans;

import app.domain.entity.Sector;
import app.domain.model.binding.JobCreationBindingModel;
import app.domain.model.service.JobApplicationServiceModel;
import app.service.JobApplicationService;
import org.hibernate.TransactionException;
import org.modelmapper.ModelMapper;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class JobCreationBean extends BaseBean {

    private JobCreationBindingModel job;

    private JobApplicationService jobApplicationService;
    private ModelMapper modelMapper;

    public JobCreationBean() {
    }

    @Inject
    public JobCreationBean(JobApplicationService jobApplicationService, ModelMapper modelMapper) {
        this.jobApplicationService = jobApplicationService;
        this.modelMapper = modelMapper;
    }

    @PostConstruct
    private void init() {
        this.job = new JobCreationBindingModel();
    }

    public void addJob() {
        JobApplicationServiceModel jobServiceModel = this.modelMapper.map(job, JobApplicationServiceModel.class);
        jobServiceModel.setSector(Sector.valueOf(this.job.getSector().toUpperCase()));

        try {
            this.jobApplicationService.save(jobServiceModel);
        } catch (TransactionException e) {
            e.printStackTrace();
            return;
        }

        this.redirect("/home");
    }

    public JobCreationBindingModel getJob() {
        return this.job;
    }

    public void setJob(JobCreationBindingModel job) {
        this.job = job;
    }
}

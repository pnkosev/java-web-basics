package app.web.beans;

import app.service.JobApplicationService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class JobDeleteBean extends BaseBean {

    private JobApplicationService jobApplicationService;

    public JobDeleteBean() {
    }

    @Inject
    public JobDeleteBean(JobApplicationService jobApplicationService) {
        this.jobApplicationService = jobApplicationService;
    }

    public void delete() {
        String id = this.getParamFromQuery("id");
        this.jobApplicationService.deleteById(id);
        this.redirect("/home");
    }
}

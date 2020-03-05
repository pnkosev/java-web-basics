package app.domain.model.service;

import app.domain.entity.Sector;
import java.math.BigDecimal;

public class JobApplicationServiceModel {

    private String id;
    private Sector Sector;
    private String profession;
    private BigDecimal salary;
    private String description;

    public JobApplicationServiceModel() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public app.domain.entity.Sector getSector() {
        return this.Sector;
    }

    public void setSector(app.domain.entity.Sector sector) {
        Sector = sector;
    }

    public String getProfession() {
        return this.profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public BigDecimal getSalary() {
        return this.salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

package app.domain.model.view;

import java.math.BigDecimal;

public class JobViewModel {

    private String id;

    private String Sector;

    private String profession;

    private BigDecimal salary;

    private String description;

    public JobViewModel() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSector() {
        return this.Sector;
    }

    public void setSector(String sector) {
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

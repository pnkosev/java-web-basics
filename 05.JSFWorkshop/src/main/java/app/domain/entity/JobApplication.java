package app.domain.entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "job_application")
public class JobApplication extends BaseEntity {

    @Column
    @Enumerated(EnumType.STRING)
    private Sector Sector;

    @Column
    private String profession;

    @Column
    private BigDecimal salary;

    @Column
    private String description;

    public JobApplication() {
    }

    public app.domain.entity.Sector getSector() { return this.Sector; }

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

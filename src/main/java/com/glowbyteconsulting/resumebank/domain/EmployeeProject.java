package com.glowbyteconsulting.resumebank.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A EmployeeProject.
 */
@Entity
@Table(name = "employee_project")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EmployeeProject implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "id_project")
    private Long idProject;

    @Column(name = "id_role")
    private Long idRole;

    @Column(name = "responsibility_nm")
    private String responsibilityNm;

    @Column(name = "start_dt")
    private Instant startDt;

    @Column(name = "end_dt")
    private Instant endDt;

    @ManyToOne
    @JsonIgnoreProperties(value = "emails", allowSetters = true)
    private Employee email;

    @ManyToOne
    @JsonIgnoreProperties(value = "idProjects", allowSetters = true)
    private Project idProject;

    @ManyToOne
    @JsonIgnoreProperties(value = "idRoles", allowSetters = true)
    private ProjectRole idRole;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public EmployeeProject email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getIdProject() {
        return idProject;
    }

    public EmployeeProject idProject(Long idProject) {
        this.idProject = idProject;
        return this;
    }

    public void setIdProject(Long idProject) {
        this.idProject = idProject;
    }

    public Long getIdRole() {
        return idRole;
    }

    public EmployeeProject idRole(Long idRole) {
        this.idRole = idRole;
        return this;
    }

    public void setIdRole(Long idRole) {
        this.idRole = idRole;
    }

    public String getResponsibilityNm() {
        return responsibilityNm;
    }

    public EmployeeProject responsibilityNm(String responsibilityNm) {
        this.responsibilityNm = responsibilityNm;
        return this;
    }

    public void setResponsibilityNm(String responsibilityNm) {
        this.responsibilityNm = responsibilityNm;
    }

    public Instant getStartDt() {
        return startDt;
    }

    public EmployeeProject startDt(Instant startDt) {
        this.startDt = startDt;
        return this;
    }

    public void setStartDt(Instant startDt) {
        this.startDt = startDt;
    }

    public Instant getEndDt() {
        return endDt;
    }

    public EmployeeProject endDt(Instant endDt) {
        this.endDt = endDt;
        return this;
    }

    public void setEndDt(Instant endDt) {
        this.endDt = endDt;
    }

    public Employee getEmail() {
        return email;
    }

    public EmployeeProject email(Employee employee) {
        this.email = employee;
        return this;
    }

    public void setEmail(Employee employee) {
        this.email = employee;
    }

    public Project getIdProject() {
        return idProject;
    }

    public EmployeeProject idProject(Project project) {
        this.idProject = project;
        return this;
    }

    public void setIdProject(Project project) {
        this.idProject = project;
    }

    public ProjectRole getIdRole() {
        return idRole;
    }

    public EmployeeProject idRole(ProjectRole projectRole) {
        this.idRole = projectRole;
        return this;
    }

    public void setIdRole(ProjectRole projectRole) {
        this.idRole = projectRole;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmployeeProject)) {
            return false;
        }
        return id != null && id.equals(((EmployeeProject) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeProject{" +
            "id=" + getId() +
            ", email='" + getEmail() + "'" +
            ", idProject=" + getIdProject() +
            ", idRole=" + getIdRole() +
            ", responsibilityNm='" + getResponsibilityNm() + "'" +
            ", startDt='" + getStartDt() + "'" +
            ", endDt='" + getEndDt() + "'" +
            "}";
    }
}

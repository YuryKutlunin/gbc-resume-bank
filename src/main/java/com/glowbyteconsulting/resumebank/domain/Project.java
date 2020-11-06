package com.glowbyteconsulting.resumebank.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Project.
 */
@Entity
@Table(name = "project")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "id_project")
    private Long idProject;

    @Column(name = "project_nm")
    private String projectNm;

    @Column(name = "company_nm")
    private String companyNM;

    @Column(name = "start_dt")
    private Instant startDt;

    @Column(name = "end_dt")
    private Instant endDt;

    @OneToMany(mappedBy = "idProject")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<ProjectTechnology> idProjects = new HashSet<>();

    @OneToMany(mappedBy = "idProject")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<EmployeeProject> idProjects = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdProject() {
        return idProject;
    }

    public Project idProject(Long idProject) {
        this.idProject = idProject;
        return this;
    }

    public void setIdProject(Long idProject) {
        this.idProject = idProject;
    }

    public String getProjectNm() {
        return projectNm;
    }

    public Project projectNm(String projectNm) {
        this.projectNm = projectNm;
        return this;
    }

    public void setProjectNm(String projectNm) {
        this.projectNm = projectNm;
    }

    public String getCompanyNM() {
        return companyNM;
    }

    public Project companyNM(String companyNM) {
        this.companyNM = companyNM;
        return this;
    }

    public void setCompanyNM(String companyNM) {
        this.companyNM = companyNM;
    }

    public Instant getStartDt() {
        return startDt;
    }

    public Project startDt(Instant startDt) {
        this.startDt = startDt;
        return this;
    }

    public void setStartDt(Instant startDt) {
        this.startDt = startDt;
    }

    public Instant getEndDt() {
        return endDt;
    }

    public Project endDt(Instant endDt) {
        this.endDt = endDt;
        return this;
    }

    public void setEndDt(Instant endDt) {
        this.endDt = endDt;
    }

    public Set<ProjectTechnology> getIdProjects() {
        return idProjects;
    }

    public Project idProjects(Set<ProjectTechnology> projectTechnologies) {
        this.idProjects = projectTechnologies;
        return this;
    }

    public Project addIdProject(ProjectTechnology projectTechnology) {
        this.idProjects.add(projectTechnology);
        projectTechnology.setIdProject(this);
        return this;
    }

    public Project removeIdProject(ProjectTechnology projectTechnology) {
        this.idProjects.remove(projectTechnology);
        projectTechnology.setIdProject(null);
        return this;
    }

    public void setIdProjects(Set<ProjectTechnology> projectTechnologies) {
        this.idProjects = projectTechnologies;
    }

    public Set<EmployeeProject> getIdProjects() {
        return idProjects;
    }

    public Project idProjects(Set<EmployeeProject> employeeProjects) {
        this.idProjects = employeeProjects;
        return this;
    }

    public Project addIdProject(EmployeeProject employeeProject) {
        this.idProjects.add(employeeProject);
        employeeProject.setIdProject(this);
        return this;
    }

    public Project removeIdProject(EmployeeProject employeeProject) {
        this.idProjects.remove(employeeProject);
        employeeProject.setIdProject(null);
        return this;
    }

    public void setIdProjects(Set<EmployeeProject> employeeProjects) {
        this.idProjects = employeeProjects;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Project)) {
            return false;
        }
        return id != null && id.equals(((Project) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Project{" +
            "id=" + getId() +
            ", idProject=" + getIdProject() +
            ", projectNm='" + getProjectNm() + "'" +
            ", companyNM='" + getCompanyNM() + "'" +
            ", startDt='" + getStartDt() + "'" +
            ", endDt='" + getEndDt() + "'" +
            "}";
    }
}

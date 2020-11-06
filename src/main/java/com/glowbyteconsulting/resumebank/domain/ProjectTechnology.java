package com.glowbyteconsulting.resumebank.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A ProjectTechnology.
 */
@Entity
@Table(name = "project_technology")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProjectTechnology implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "id_project")
    private Long idProject;

    @Column(name = "id_technology")
    private Long idTechnology;

    @ManyToOne
    @JsonIgnoreProperties(value = "idProjects", allowSetters = true)
    private Project idProject;

    @ManyToOne
    @JsonIgnoreProperties(value = "idTechnologies", allowSetters = true)
    private Technology idProject;

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

    public ProjectTechnology idProject(Long idProject) {
        this.idProject = idProject;
        return this;
    }

    public void setIdProject(Long idProject) {
        this.idProject = idProject;
    }

    public Long getIdTechnology() {
        return idTechnology;
    }

    public ProjectTechnology idTechnology(Long idTechnology) {
        this.idTechnology = idTechnology;
        return this;
    }

    public void setIdTechnology(Long idTechnology) {
        this.idTechnology = idTechnology;
    }

    public Project getIdProject() {
        return idProject;
    }

    public ProjectTechnology idProject(Project project) {
        this.idProject = project;
        return this;
    }

    public void setIdProject(Project project) {
        this.idProject = project;
    }

    public Technology getIdProject() {
        return idProject;
    }

    public ProjectTechnology idProject(Technology technology) {
        this.idProject = technology;
        return this;
    }

    public void setIdProject(Technology technology) {
        this.idProject = technology;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProjectTechnology)) {
            return false;
        }
        return id != null && id.equals(((ProjectTechnology) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProjectTechnology{" +
            "id=" + getId() +
            ", idProject=" + getIdProject() +
            ", idTechnology=" + getIdTechnology() +
            "}";
    }
}

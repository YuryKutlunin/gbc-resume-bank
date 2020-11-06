package com.glowbyteconsulting.resumebank.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A JobTitle.
 */
@Entity
@Table(name = "job_title")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class JobTitle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "id_title")
    private Long idTitle;

    @Column(name = "title_nm")
    private String titleNM;

    @OneToMany(mappedBy = "idTitle")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Employee> idTitles = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdTitle() {
        return idTitle;
    }

    public JobTitle idTitle(Long idTitle) {
        this.idTitle = idTitle;
        return this;
    }

    public void setIdTitle(Long idTitle) {
        this.idTitle = idTitle;
    }

    public String getTitleNM() {
        return titleNM;
    }

    public JobTitle titleNM(String titleNM) {
        this.titleNM = titleNM;
        return this;
    }

    public void setTitleNM(String titleNM) {
        this.titleNM = titleNM;
    }

    public Set<Employee> getIdTitles() {
        return idTitles;
    }

    public JobTitle idTitles(Set<Employee> employees) {
        this.idTitles = employees;
        return this;
    }

    public JobTitle addIdTitle(Employee employee) {
        this.idTitles.add(employee);
        employee.setIdTitle(this);
        return this;
    }

    public JobTitle removeIdTitle(Employee employee) {
        this.idTitles.remove(employee);
        employee.setIdTitle(null);
        return this;
    }

    public void setIdTitles(Set<Employee> employees) {
        this.idTitles = employees;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JobTitle)) {
            return false;
        }
        return id != null && id.equals(((JobTitle) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "JobTitle{" +
            "id=" + getId() +
            ", idTitle=" + getIdTitle() +
            ", titleNM='" + getTitleNM() + "'" +
            "}";
    }
}

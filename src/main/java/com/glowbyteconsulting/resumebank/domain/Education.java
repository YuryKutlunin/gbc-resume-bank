package com.glowbyteconsulting.resumebank.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Education.
 */
@Entity
@Table(name = "education")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Education implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "id_univer")
    private Long idUniver;

    @Column(name = "id_educ_type")
    private String idEducType;

    @Column(name = "faculty")
    private String faculty;

    @Column(name = "specialty")
    private String specialty;

    @Column(name = "specialization")
    private String specialization;

    @Column(name = "start_year")
    private Long startYear;

    @Column(name = "end_year")
    private Long endYear;

    @ManyToOne
    @JsonIgnoreProperties(value = "emails", allowSetters = true)
    private Employee email;

    @ManyToOne
    @JsonIgnoreProperties(value = "idUnivers", allowSetters = true)
    private University idUniver;

    @ManyToOne
    @JsonIgnoreProperties(value = "idEducTypes", allowSetters = true)
    private EducType idEducType;

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

    public Education email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getIdUniver() {
        return idUniver;
    }

    public Education idUniver(Long idUniver) {
        this.idUniver = idUniver;
        return this;
    }

    public void setIdUniver(Long idUniver) {
        this.idUniver = idUniver;
    }

    public String getIdEducType() {
        return idEducType;
    }

    public Education idEducType(String idEducType) {
        this.idEducType = idEducType;
        return this;
    }

    public void setIdEducType(String idEducType) {
        this.idEducType = idEducType;
    }

    public String getFaculty() {
        return faculty;
    }

    public Education faculty(String faculty) {
        this.faculty = faculty;
        return this;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getSpecialty() {
        return specialty;
    }

    public Education specialty(String specialty) {
        this.specialty = specialty;
        return this;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getSpecialization() {
        return specialization;
    }

    public Education specialization(String specialization) {
        this.specialization = specialization;
        return this;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public Long getStartYear() {
        return startYear;
    }

    public Education startYear(Long startYear) {
        this.startYear = startYear;
        return this;
    }

    public void setStartYear(Long startYear) {
        this.startYear = startYear;
    }

    public Long getEndYear() {
        return endYear;
    }

    public Education endYear(Long endYear) {
        this.endYear = endYear;
        return this;
    }

    public void setEndYear(Long endYear) {
        this.endYear = endYear;
    }

    public Employee getEmail() {
        return email;
    }

    public Education email(Employee employee) {
        this.email = employee;
        return this;
    }

    public void setEmail(Employee employee) {
        this.email = employee;
    }

    public University getIdUniver() {
        return idUniver;
    }

    public Education idUniver(University university) {
        this.idUniver = university;
        return this;
    }

    public void setIdUniver(University university) {
        this.idUniver = university;
    }

    public EducType getIdEducType() {
        return idEducType;
    }

    public Education idEducType(EducType educType) {
        this.idEducType = educType;
        return this;
    }

    public void setIdEducType(EducType educType) {
        this.idEducType = educType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Education)) {
            return false;
        }
        return id != null && id.equals(((Education) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Education{" +
            "id=" + getId() +
            ", email='" + getEmail() + "'" +
            ", idUniver=" + getIdUniver() +
            ", idEducType='" + getIdEducType() + "'" +
            ", faculty='" + getFaculty() + "'" +
            ", specialty='" + getSpecialty() + "'" +
            ", specialization='" + getSpecialization() + "'" +
            ", startYear=" + getStartYear() +
            ", endYear=" + getEndYear() +
            "}";
    }
}

package com.glowbyteconsulting.resumebank.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A EmployeeCertif.
 */
@Entity
@Table(name = "employee_certif")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EmployeeCertif implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "id_certificate")
    private Long idCertificate;

    @Column(name = "start_dt")
    private Instant startDt;

    @Column(name = "end_dt")
    private Instant endDt;

    @ManyToOne
    @JsonIgnoreProperties(value = "emails", allowSetters = true)
    private Employee email;

    @ManyToOne
    @JsonIgnoreProperties(value = "idCertificates", allowSetters = true)
    private Certificate idCertificate;

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

    public EmployeeCertif email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getIdCertificate() {
        return idCertificate;
    }

    public EmployeeCertif idCertificate(Long idCertificate) {
        this.idCertificate = idCertificate;
        return this;
    }

    public void setIdCertificate(Long idCertificate) {
        this.idCertificate = idCertificate;
    }

    public Instant getStartDt() {
        return startDt;
    }

    public EmployeeCertif startDt(Instant startDt) {
        this.startDt = startDt;
        return this;
    }

    public void setStartDt(Instant startDt) {
        this.startDt = startDt;
    }

    public Instant getEndDt() {
        return endDt;
    }

    public EmployeeCertif endDt(Instant endDt) {
        this.endDt = endDt;
        return this;
    }

    public void setEndDt(Instant endDt) {
        this.endDt = endDt;
    }

    public Employee getEmail() {
        return email;
    }

    public EmployeeCertif email(Employee employee) {
        this.email = employee;
        return this;
    }

    public void setEmail(Employee employee) {
        this.email = employee;
    }

    public Certificate getIdCertificate() {
        return idCertificate;
    }

    public EmployeeCertif idCertificate(Certificate certificate) {
        this.idCertificate = certificate;
        return this;
    }

    public void setIdCertificate(Certificate certificate) {
        this.idCertificate = certificate;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmployeeCertif)) {
            return false;
        }
        return id != null && id.equals(((EmployeeCertif) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeCertif{" +
            "id=" + getId() +
            ", email='" + getEmail() + "'" +
            ", idCertificate=" + getIdCertificate() +
            ", startDt='" + getStartDt() + "'" +
            ", endDt='" + getEndDt() + "'" +
            "}";
    }
}

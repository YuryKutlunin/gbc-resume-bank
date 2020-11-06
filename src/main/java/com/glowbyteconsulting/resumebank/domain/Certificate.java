package com.glowbyteconsulting.resumebank.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Certificate.
 */
@Entity
@Table(name = "certificate")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Certificate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "id_certificate")
    private Long idCertificate;

    @Column(name = "certificate_nm")
    private String certificateNm;

    @Column(name = "cert_scope_nm")
    private String certScopeNm;

    @OneToMany(mappedBy = "idCertificate")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<EmployeeCertif> idCertificates = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdCertificate() {
        return idCertificate;
    }

    public Certificate idCertificate(Long idCertificate) {
        this.idCertificate = idCertificate;
        return this;
    }

    public void setIdCertificate(Long idCertificate) {
        this.idCertificate = idCertificate;
    }

    public String getCertificateNm() {
        return certificateNm;
    }

    public Certificate certificateNm(String certificateNm) {
        this.certificateNm = certificateNm;
        return this;
    }

    public void setCertificateNm(String certificateNm) {
        this.certificateNm = certificateNm;
    }

    public String getCertScopeNm() {
        return certScopeNm;
    }

    public Certificate certScopeNm(String certScopeNm) {
        this.certScopeNm = certScopeNm;
        return this;
    }

    public void setCertScopeNm(String certScopeNm) {
        this.certScopeNm = certScopeNm;
    }

    public Set<EmployeeCertif> getIdCertificates() {
        return idCertificates;
    }

    public Certificate idCertificates(Set<EmployeeCertif> employeeCertifs) {
        this.idCertificates = employeeCertifs;
        return this;
    }

    public Certificate addIdCertificate(EmployeeCertif employeeCertif) {
        this.idCertificates.add(employeeCertif);
        employeeCertif.setIdCertificate(this);
        return this;
    }

    public Certificate removeIdCertificate(EmployeeCertif employeeCertif) {
        this.idCertificates.remove(employeeCertif);
        employeeCertif.setIdCertificate(null);
        return this;
    }

    public void setIdCertificates(Set<EmployeeCertif> employeeCertifs) {
        this.idCertificates = employeeCertifs;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Certificate)) {
            return false;
        }
        return id != null && id.equals(((Certificate) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Certificate{" +
            "id=" + getId() +
            ", idCertificate=" + getIdCertificate() +
            ", certificateNm='" + getCertificateNm() + "'" +
            ", certScopeNm='" + getCertScopeNm() + "'" +
            "}";
    }
}

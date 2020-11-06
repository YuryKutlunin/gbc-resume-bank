package com.glowbyteconsulting.resumebank.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A University.
 */
@Entity
@Table(name = "university")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class University implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "id_univer")
    private Long idUniver;

    @Column(name = "univer_nm")
    private String univerNm;

    @OneToMany(mappedBy = "idUniver")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Education> idUnivers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdUniver() {
        return idUniver;
    }

    public University idUniver(Long idUniver) {
        this.idUniver = idUniver;
        return this;
    }

    public void setIdUniver(Long idUniver) {
        this.idUniver = idUniver;
    }

    public String getUniverNm() {
        return univerNm;
    }

    public University univerNm(String univerNm) {
        this.univerNm = univerNm;
        return this;
    }

    public void setUniverNm(String univerNm) {
        this.univerNm = univerNm;
    }

    public Set<Education> getIdUnivers() {
        return idUnivers;
    }

    public University idUnivers(Set<Education> educations) {
        this.idUnivers = educations;
        return this;
    }

    public University addIdUniver(Education education) {
        this.idUnivers.add(education);
        education.setIdUniver(this);
        return this;
    }

    public University removeIdUniver(Education education) {
        this.idUnivers.remove(education);
        education.setIdUniver(null);
        return this;
    }

    public void setIdUnivers(Set<Education> educations) {
        this.idUnivers = educations;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof University)) {
            return false;
        }
        return id != null && id.equals(((University) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "University{" +
            "id=" + getId() +
            ", idUniver=" + getIdUniver() +
            ", univerNm='" + getUniverNm() + "'" +
            "}";
    }
}

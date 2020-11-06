package com.glowbyteconsulting.resumebank.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A ResourcePool.
 */
@Entity
@Table(name = "resource_pool")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ResourcePool implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "resource_pool_code")
    private String resourcePoolCode;

    @Column(name = "resource_pool_nm")
    private String resourcePoolNm;

    @Column(name = "pool_leader")
    private String poolLeader;

    @OneToMany(mappedBy = "idResourcePool")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Employee> idResourcePools = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResourcePoolCode() {
        return resourcePoolCode;
    }

    public ResourcePool resourcePoolCode(String resourcePoolCode) {
        this.resourcePoolCode = resourcePoolCode;
        return this;
    }

    public void setResourcePoolCode(String resourcePoolCode) {
        this.resourcePoolCode = resourcePoolCode;
    }

    public String getResourcePoolNm() {
        return resourcePoolNm;
    }

    public ResourcePool resourcePoolNm(String resourcePoolNm) {
        this.resourcePoolNm = resourcePoolNm;
        return this;
    }

    public void setResourcePoolNm(String resourcePoolNm) {
        this.resourcePoolNm = resourcePoolNm;
    }

    public String getPoolLeader() {
        return poolLeader;
    }

    public ResourcePool poolLeader(String poolLeader) {
        this.poolLeader = poolLeader;
        return this;
    }

    public void setPoolLeader(String poolLeader) {
        this.poolLeader = poolLeader;
    }

    public Set<Employee> getIdResourcePools() {
        return idResourcePools;
    }

    public ResourcePool idResourcePools(Set<Employee> employees) {
        this.idResourcePools = employees;
        return this;
    }

    public ResourcePool addIdResourcePool(Employee employee) {
        this.idResourcePools.add(employee);
        employee.setIdResourcePool(this);
        return this;
    }

    public ResourcePool removeIdResourcePool(Employee employee) {
        this.idResourcePools.remove(employee);
        employee.setIdResourcePool(null);
        return this;
    }

    public void setIdResourcePools(Set<Employee> employees) {
        this.idResourcePools = employees;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ResourcePool)) {
            return false;
        }
        return id != null && id.equals(((ResourcePool) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ResourcePool{" +
            "id=" + getId() +
            ", resourcePoolCode='" + getResourcePoolCode() + "'" +
            ", resourcePoolNm='" + getResourcePoolNm() + "'" +
            ", poolLeader='" + getPoolLeader() + "'" +
            "}";
    }
}

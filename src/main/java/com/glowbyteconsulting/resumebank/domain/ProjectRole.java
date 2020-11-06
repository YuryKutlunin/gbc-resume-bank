package com.glowbyteconsulting.resumebank.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A ProjectRole.
 */
@Entity
@Table(name = "project_role")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProjectRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "id_role")
    private Long idRole;

    @Column(name = "role_nm")
    private String roleNm;

    @OneToMany(mappedBy = "idRole")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<EmployeeProject> idRoles = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdRole() {
        return idRole;
    }

    public ProjectRole idRole(Long idRole) {
        this.idRole = idRole;
        return this;
    }

    public void setIdRole(Long idRole) {
        this.idRole = idRole;
    }

    public String getRoleNm() {
        return roleNm;
    }

    public ProjectRole roleNm(String roleNm) {
        this.roleNm = roleNm;
        return this;
    }

    public void setRoleNm(String roleNm) {
        this.roleNm = roleNm;
    }

    public Set<EmployeeProject> getIdRoles() {
        return idRoles;
    }

    public ProjectRole idRoles(Set<EmployeeProject> employeeProjects) {
        this.idRoles = employeeProjects;
        return this;
    }

    public ProjectRole addIdRole(EmployeeProject employeeProject) {
        this.idRoles.add(employeeProject);
        employeeProject.setIdRole(this);
        return this;
    }

    public ProjectRole removeIdRole(EmployeeProject employeeProject) {
        this.idRoles.remove(employeeProject);
        employeeProject.setIdRole(null);
        return this;
    }

    public void setIdRoles(Set<EmployeeProject> employeeProjects) {
        this.idRoles = employeeProjects;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProjectRole)) {
            return false;
        }
        return id != null && id.equals(((ProjectRole) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProjectRole{" +
            "id=" + getId() +
            ", idRole=" + getIdRole() +
            ", roleNm='" + getRoleNm() + "'" +
            "}";
    }
}

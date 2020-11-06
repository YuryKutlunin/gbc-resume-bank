import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './project-role.reducer';
import { IProjectRole } from 'app/shared/model/project-role.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IProjectRoleProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const ProjectRole = (props: IProjectRoleProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { projectRoleList, match, loading } = props;
  return (
    <div>
      <h2 id="project-role-heading">
        <Translate contentKey="gbcResumeBankApp.projectRole.home.title">Project Roles</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="gbcResumeBankApp.projectRole.home.createLabel">Create new Project Role</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {projectRoleList && projectRoleList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="gbcResumeBankApp.projectRole.idRole">Id Role</Translate>
                </th>
                <th>
                  <Translate contentKey="gbcResumeBankApp.projectRole.roleNm">Role Nm</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {projectRoleList.map((projectRole, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${projectRole.id}`} color="link" size="sm">
                      {projectRole.id}
                    </Button>
                  </td>
                  <td>{projectRole.idRole}</td>
                  <td>{projectRole.roleNm}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${projectRole.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${projectRole.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${projectRole.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="gbcResumeBankApp.projectRole.home.notFound">No Project Roles found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ projectRole }: IRootState) => ({
  projectRoleList: projectRole.entities,
  loading: projectRole.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ProjectRole);

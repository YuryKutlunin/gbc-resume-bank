import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './project-technology.reducer';
import { IProjectTechnology } from 'app/shared/model/project-technology.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IProjectTechnologyProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const ProjectTechnology = (props: IProjectTechnologyProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { projectTechnologyList, match, loading } = props;
  return (
    <div>
      <h2 id="project-technology-heading">
        <Translate contentKey="gbcResumeBankApp.projectTechnology.home.title">Project Technologies</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="gbcResumeBankApp.projectTechnology.home.createLabel">Create new Project Technology</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {projectTechnologyList && projectTechnologyList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="gbcResumeBankApp.projectTechnology.idProject">Id Project</Translate>
                </th>
                <th>
                  <Translate contentKey="gbcResumeBankApp.projectTechnology.idTechnology">Id Technology</Translate>
                </th>
                <th>
                  <Translate contentKey="gbcResumeBankApp.projectTechnology.idProject">Id Project</Translate>
                </th>
                <th>
                  <Translate contentKey="gbcResumeBankApp.projectTechnology.idProject">Id Project</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {projectTechnologyList.map((projectTechnology, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${projectTechnology.id}`} color="link" size="sm">
                      {projectTechnology.id}
                    </Button>
                  </td>
                  <td>{projectTechnology.idProject}</td>
                  <td>{projectTechnology.idTechnology}</td>
                  <td>
                    {projectTechnology.idProject ? (
                      <Link to={`project/${projectTechnology.idProject.id}`}>{projectTechnology.idProject.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {projectTechnology.idProject ? (
                      <Link to={`technology/${projectTechnology.idProject.id}`}>{projectTechnology.idProject.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${projectTechnology.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${projectTechnology.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${projectTechnology.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="gbcResumeBankApp.projectTechnology.home.notFound">No Project Technologies found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ projectTechnology }: IRootState) => ({
  projectTechnologyList: projectTechnology.entities,
  loading: projectTechnology.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ProjectTechnology);

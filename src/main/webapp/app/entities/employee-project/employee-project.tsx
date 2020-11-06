import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './employee-project.reducer';
import { IEmployeeProject } from 'app/shared/model/employee-project.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEmployeeProjectProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const EmployeeProject = (props: IEmployeeProjectProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { employeeProjectList, match, loading } = props;
  return (
    <div>
      <h2 id="employee-project-heading">
        <Translate contentKey="gbcResumeBankApp.employeeProject.home.title">Employee Projects</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="gbcResumeBankApp.employeeProject.home.createLabel">Create new Employee Project</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {employeeProjectList && employeeProjectList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="gbcResumeBankApp.employeeProject.email">Email</Translate>
                </th>
                <th>
                  <Translate contentKey="gbcResumeBankApp.employeeProject.idProject">Id Project</Translate>
                </th>
                <th>
                  <Translate contentKey="gbcResumeBankApp.employeeProject.idRole">Id Role</Translate>
                </th>
                <th>
                  <Translate contentKey="gbcResumeBankApp.employeeProject.responsibilityNm">Responsibility Nm</Translate>
                </th>
                <th>
                  <Translate contentKey="gbcResumeBankApp.employeeProject.startDt">Start Dt</Translate>
                </th>
                <th>
                  <Translate contentKey="gbcResumeBankApp.employeeProject.endDt">End Dt</Translate>
                </th>
                <th>
                  <Translate contentKey="gbcResumeBankApp.employeeProject.email">Email</Translate>
                </th>
                <th>
                  <Translate contentKey="gbcResumeBankApp.employeeProject.idProject">Id Project</Translate>
                </th>
                <th>
                  <Translate contentKey="gbcResumeBankApp.employeeProject.idRole">Id Role</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {employeeProjectList.map((employeeProject, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${employeeProject.id}`} color="link" size="sm">
                      {employeeProject.id}
                    </Button>
                  </td>
                  <td>{employeeProject.email}</td>
                  <td>{employeeProject.idProject}</td>
                  <td>{employeeProject.idRole}</td>
                  <td>{employeeProject.responsibilityNm}</td>
                  <td>
                    {employeeProject.startDt ? <TextFormat type="date" value={employeeProject.startDt} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    {employeeProject.endDt ? <TextFormat type="date" value={employeeProject.endDt} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    {employeeProject.email ? <Link to={`employee/${employeeProject.email.id}`}>{employeeProject.email.id}</Link> : ''}
                  </td>
                  <td>
                    {employeeProject.idProject ? (
                      <Link to={`project/${employeeProject.idProject.id}`}>{employeeProject.idProject.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {employeeProject.idRole ? (
                      <Link to={`project-role/${employeeProject.idRole.id}`}>{employeeProject.idRole.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${employeeProject.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${employeeProject.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${employeeProject.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="gbcResumeBankApp.employeeProject.home.notFound">No Employee Projects found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ employeeProject }: IRootState) => ({
  employeeProjectList: employeeProject.entities,
  loading: employeeProject.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EmployeeProject);

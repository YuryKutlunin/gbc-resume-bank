import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './employee.reducer';
import { IEmployee } from 'app/shared/model/employee.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEmployeeProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Employee = (props: IEmployeeProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { employeeList, match, loading } = props;
  return (
    <div>
      <h2 id="employee-heading">
        <Translate contentKey="gbcResumeBankApp.employee.home.title">Employees</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="gbcResumeBankApp.employee.home.createLabel">Create new Employee</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {employeeList && employeeList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="gbcResumeBankApp.employee.firstNm">First Nm</Translate>
                </th>
                <th>
                  <Translate contentKey="gbcResumeBankApp.employee.lastNm">Last Nm</Translate>
                </th>
                <th>
                  <Translate contentKey="gbcResumeBankApp.employee.middleNm">Middle Nm</Translate>
                </th>
                <th>
                  <Translate contentKey="gbcResumeBankApp.employee.email">Email</Translate>
                </th>
                <th>
                  <Translate contentKey="gbcResumeBankApp.employee.phoneNum">Phone Num</Translate>
                </th>
                <th>
                  <Translate contentKey="gbcResumeBankApp.employee.workType">Work Type</Translate>
                </th>
                <th>
                  <Translate contentKey="gbcResumeBankApp.employee.birthDt">Birth Dt</Translate>
                </th>
                <th>
                  <Translate contentKey="gbcResumeBankApp.employee.idTitle">Id Title</Translate>
                </th>
                <th>
                  <Translate contentKey="gbcResumeBankApp.employee.resourcePoolCode">Resource Pool Code</Translate>
                </th>
                <th>
                  <Translate contentKey="gbcResumeBankApp.employee.emailCurator">Email Curator</Translate>
                </th>
                <th>
                  <Translate contentKey="gbcResumeBankApp.employee.email">Email</Translate>
                </th>
                <th>
                  <Translate contentKey="gbcResumeBankApp.employee.idResourcePool">Id Resource Pool</Translate>
                </th>
                <th>
                  <Translate contentKey="gbcResumeBankApp.employee.idTitle">Id Title</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {employeeList.map((employee, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${employee.id}`} color="link" size="sm">
                      {employee.id}
                    </Button>
                  </td>
                  <td>{employee.firstNm}</td>
                  <td>{employee.lastNm}</td>
                  <td>{employee.middleNm}</td>
                  <td>{employee.email}</td>
                  <td>{employee.phoneNum}</td>
                  <td>{employee.workType}</td>
                  <td>{employee.birthDt ? <TextFormat type="date" value={employee.birthDt} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{employee.idTitle}</td>
                  <td>{employee.resourcePoolCode}</td>
                  <td>{employee.emailCurator}</td>
                  <td>{employee.email ? <Link to={`employee/${employee.email.id}`}>{employee.email.id}</Link> : ''}</td>
                  <td>
                    {employee.idResourcePool ? (
                      <Link to={`resource-pool/${employee.idResourcePool.id}`}>{employee.idResourcePool.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>{employee.idTitle ? <Link to={`job-title/${employee.idTitle.id}`}>{employee.idTitle.id}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${employee.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${employee.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${employee.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="gbcResumeBankApp.employee.home.notFound">No Employees found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ employee }: IRootState) => ({
  employeeList: employee.entities,
  loading: employee.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Employee);

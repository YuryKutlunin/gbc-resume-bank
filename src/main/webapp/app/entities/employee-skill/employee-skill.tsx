import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './employee-skill.reducer';
import { IEmployeeSkill } from 'app/shared/model/employee-skill.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEmployeeSkillProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const EmployeeSkill = (props: IEmployeeSkillProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { employeeSkillList, match, loading } = props;
  return (
    <div>
      <h2 id="employee-skill-heading">
        <Translate contentKey="gbcResumeBankApp.employeeSkill.home.title">Employee Skills</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="gbcResumeBankApp.employeeSkill.home.createLabel">Create new Employee Skill</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {employeeSkillList && employeeSkillList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="gbcResumeBankApp.employeeSkill.email">Email</Translate>
                </th>
                <th>
                  <Translate contentKey="gbcResumeBankApp.employeeSkill.idSkill">Id Skill</Translate>
                </th>
                <th>
                  <Translate contentKey="gbcResumeBankApp.employeeSkill.idLevel">Id Level</Translate>
                </th>
                <th>
                  <Translate contentKey="gbcResumeBankApp.employeeSkill.email">Email</Translate>
                </th>
                <th>
                  <Translate contentKey="gbcResumeBankApp.employeeSkill.idSkill">Id Skill</Translate>
                </th>
                <th>
                  <Translate contentKey="gbcResumeBankApp.employeeSkill.idLevel">Id Level</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {employeeSkillList.map((employeeSkill, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${employeeSkill.id}`} color="link" size="sm">
                      {employeeSkill.id}
                    </Button>
                  </td>
                  <td>{employeeSkill.email}</td>
                  <td>{employeeSkill.idSkill}</td>
                  <td>{employeeSkill.idLevel}</td>
                  <td>{employeeSkill.email ? <Link to={`employee/${employeeSkill.email.id}`}>{employeeSkill.email.id}</Link> : ''}</td>
                  <td>{employeeSkill.idSkill ? <Link to={`skill/${employeeSkill.idSkill.id}`}>{employeeSkill.idSkill.id}</Link> : ''}</td>
                  <td>
                    {employeeSkill.idLevel ? <Link to={`skill-level/${employeeSkill.idLevel.id}`}>{employeeSkill.idLevel.id}</Link> : ''}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${employeeSkill.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${employeeSkill.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${employeeSkill.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="gbcResumeBankApp.employeeSkill.home.notFound">No Employee Skills found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ employeeSkill }: IRootState) => ({
  employeeSkillList: employeeSkill.entities,
  loading: employeeSkill.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EmployeeSkill);

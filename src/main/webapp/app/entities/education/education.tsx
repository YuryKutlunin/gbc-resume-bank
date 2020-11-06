import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './education.reducer';
import { IEducation } from 'app/shared/model/education.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEducationProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Education = (props: IEducationProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { educationList, match, loading } = props;
  return (
    <div>
      <h2 id="education-heading">
        <Translate contentKey="gbcResumeBankApp.education.home.title">Educations</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="gbcResumeBankApp.education.home.createLabel">Create new Education</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {educationList && educationList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="gbcResumeBankApp.education.email">Email</Translate>
                </th>
                <th>
                  <Translate contentKey="gbcResumeBankApp.education.idUniver">Id Univer</Translate>
                </th>
                <th>
                  <Translate contentKey="gbcResumeBankApp.education.idEducType">Id Educ Type</Translate>
                </th>
                <th>
                  <Translate contentKey="gbcResumeBankApp.education.faculty">Faculty</Translate>
                </th>
                <th>
                  <Translate contentKey="gbcResumeBankApp.education.specialty">Specialty</Translate>
                </th>
                <th>
                  <Translate contentKey="gbcResumeBankApp.education.specialization">Specialization</Translate>
                </th>
                <th>
                  <Translate contentKey="gbcResumeBankApp.education.startYear">Start Year</Translate>
                </th>
                <th>
                  <Translate contentKey="gbcResumeBankApp.education.endYear">End Year</Translate>
                </th>
                <th>
                  <Translate contentKey="gbcResumeBankApp.education.email">Email</Translate>
                </th>
                <th>
                  <Translate contentKey="gbcResumeBankApp.education.idUniver">Id Univer</Translate>
                </th>
                <th>
                  <Translate contentKey="gbcResumeBankApp.education.idEducType">Id Educ Type</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {educationList.map((education, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${education.id}`} color="link" size="sm">
                      {education.id}
                    </Button>
                  </td>
                  <td>{education.email}</td>
                  <td>{education.idUniver}</td>
                  <td>{education.idEducType}</td>
                  <td>{education.faculty}</td>
                  <td>{education.specialty}</td>
                  <td>{education.specialization}</td>
                  <td>{education.startYear}</td>
                  <td>{education.endYear}</td>
                  <td>{education.email ? <Link to={`employee/${education.email.id}`}>{education.email.id}</Link> : ''}</td>
                  <td>{education.idUniver ? <Link to={`university/${education.idUniver.id}`}>{education.idUniver.id}</Link> : ''}</td>
                  <td>{education.idEducType ? <Link to={`educ-type/${education.idEducType.id}`}>{education.idEducType.id}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${education.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${education.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${education.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="gbcResumeBankApp.education.home.notFound">No Educations found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ education }: IRootState) => ({
  educationList: education.entities,
  loading: education.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Education);

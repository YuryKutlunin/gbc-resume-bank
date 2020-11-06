import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './educ-type.reducer';
import { IEducType } from 'app/shared/model/educ-type.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEducTypeProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const EducType = (props: IEducTypeProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { educTypeList, match, loading } = props;
  return (
    <div>
      <h2 id="educ-type-heading">
        <Translate contentKey="gbcResumeBankApp.educType.home.title">Educ Types</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="gbcResumeBankApp.educType.home.createLabel">Create new Educ Type</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {educTypeList && educTypeList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="gbcResumeBankApp.educType.idEducType">Id Educ Type</Translate>
                </th>
                <th>
                  <Translate contentKey="gbcResumeBankApp.educType.educTypeNm">Educ Type Nm</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {educTypeList.map((educType, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${educType.id}`} color="link" size="sm">
                      {educType.id}
                    </Button>
                  </td>
                  <td>{educType.idEducType}</td>
                  <td>{educType.educTypeNm}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${educType.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${educType.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${educType.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="gbcResumeBankApp.educType.home.notFound">No Educ Types found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ educType }: IRootState) => ({
  educTypeList: educType.entities,
  loading: educType.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EducType);

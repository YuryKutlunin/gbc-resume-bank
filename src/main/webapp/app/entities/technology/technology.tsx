import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './technology.reducer';
import { ITechnology } from 'app/shared/model/technology.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITechnologyProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Technology = (props: ITechnologyProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { technologyList, match, loading } = props;
  return (
    <div>
      <h2 id="technology-heading">
        <Translate contentKey="gbcResumeBankApp.technology.home.title">Technologies</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="gbcResumeBankApp.technology.home.createLabel">Create new Technology</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {technologyList && technologyList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="gbcResumeBankApp.technology.idTechnology">Id Technology</Translate>
                </th>
                <th>
                  <Translate contentKey="gbcResumeBankApp.technology.technologyNm">Technology Nm</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {technologyList.map((technology, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${technology.id}`} color="link" size="sm">
                      {technology.id}
                    </Button>
                  </td>
                  <td>{technology.idTechnology}</td>
                  <td>{technology.technologyNm}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${technology.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${technology.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${technology.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="gbcResumeBankApp.technology.home.notFound">No Technologies found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ technology }: IRootState) => ({
  technologyList: technology.entities,
  loading: technology.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Technology);

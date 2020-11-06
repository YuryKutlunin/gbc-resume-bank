import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './resource-pool.reducer';
import { IResourcePool } from 'app/shared/model/resource-pool.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IResourcePoolProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const ResourcePool = (props: IResourcePoolProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { resourcePoolList, match, loading } = props;
  return (
    <div>
      <h2 id="resource-pool-heading">
        <Translate contentKey="gbcResumeBankApp.resourcePool.home.title">Resource Pools</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="gbcResumeBankApp.resourcePool.home.createLabel">Create new Resource Pool</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {resourcePoolList && resourcePoolList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="gbcResumeBankApp.resourcePool.resourcePoolCode">Resource Pool Code</Translate>
                </th>
                <th>
                  <Translate contentKey="gbcResumeBankApp.resourcePool.resourcePoolNm">Resource Pool Nm</Translate>
                </th>
                <th>
                  <Translate contentKey="gbcResumeBankApp.resourcePool.poolLeader">Pool Leader</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {resourcePoolList.map((resourcePool, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${resourcePool.id}`} color="link" size="sm">
                      {resourcePool.id}
                    </Button>
                  </td>
                  <td>{resourcePool.resourcePoolCode}</td>
                  <td>{resourcePool.resourcePoolNm}</td>
                  <td>{resourcePool.poolLeader}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${resourcePool.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${resourcePool.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${resourcePool.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="gbcResumeBankApp.resourcePool.home.notFound">No Resource Pools found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ resourcePool }: IRootState) => ({
  resourcePoolList: resourcePool.entities,
  loading: resourcePool.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ResourcePool);

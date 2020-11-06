import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './resource-pool.reducer';
import { IResourcePool } from 'app/shared/model/resource-pool.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IResourcePoolDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ResourcePoolDetail = (props: IResourcePoolDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { resourcePoolEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="gbcResumeBankApp.resourcePool.detail.title">ResourcePool</Translate> [<b>{resourcePoolEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="resourcePoolCode">
              <Translate contentKey="gbcResumeBankApp.resourcePool.resourcePoolCode">Resource Pool Code</Translate>
            </span>
          </dt>
          <dd>{resourcePoolEntity.resourcePoolCode}</dd>
          <dt>
            <span id="resourcePoolNm">
              <Translate contentKey="gbcResumeBankApp.resourcePool.resourcePoolNm">Resource Pool Nm</Translate>
            </span>
          </dt>
          <dd>{resourcePoolEntity.resourcePoolNm}</dd>
          <dt>
            <span id="poolLeader">
              <Translate contentKey="gbcResumeBankApp.resourcePool.poolLeader">Pool Leader</Translate>
            </span>
          </dt>
          <dd>{resourcePoolEntity.poolLeader}</dd>
        </dl>
        <Button tag={Link} to="/resource-pool" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/resource-pool/${resourcePoolEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ resourcePool }: IRootState) => ({
  resourcePoolEntity: resourcePool.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ResourcePoolDetail);

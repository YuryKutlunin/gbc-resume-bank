import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './resource-pool.reducer';
import { IResourcePool } from 'app/shared/model/resource-pool.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IResourcePoolUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ResourcePoolUpdate = (props: IResourcePoolUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { resourcePoolEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/resource-pool');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...resourcePoolEntity,
        ...values,
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="gbcResumeBankApp.resourcePool.home.createOrEditLabel">
            <Translate contentKey="gbcResumeBankApp.resourcePool.home.createOrEditLabel">Create or edit a ResourcePool</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : resourcePoolEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="resource-pool-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="resource-pool-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="resourcePoolCodeLabel" for="resource-pool-resourcePoolCode">
                  <Translate contentKey="gbcResumeBankApp.resourcePool.resourcePoolCode">Resource Pool Code</Translate>
                </Label>
                <AvField id="resource-pool-resourcePoolCode" type="text" name="resourcePoolCode" />
              </AvGroup>
              <AvGroup>
                <Label id="resourcePoolNmLabel" for="resource-pool-resourcePoolNm">
                  <Translate contentKey="gbcResumeBankApp.resourcePool.resourcePoolNm">Resource Pool Nm</Translate>
                </Label>
                <AvField id="resource-pool-resourcePoolNm" type="text" name="resourcePoolNm" />
              </AvGroup>
              <AvGroup>
                <Label id="poolLeaderLabel" for="resource-pool-poolLeader">
                  <Translate contentKey="gbcResumeBankApp.resourcePool.poolLeader">Pool Leader</Translate>
                </Label>
                <AvField id="resource-pool-poolLeader" type="text" name="poolLeader" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/resource-pool" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  resourcePoolEntity: storeState.resourcePool.entity,
  loading: storeState.resourcePool.loading,
  updating: storeState.resourcePool.updating,
  updateSuccess: storeState.resourcePool.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ResourcePoolUpdate);

import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './educ-type.reducer';
import { IEducType } from 'app/shared/model/educ-type.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IEducTypeUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EducTypeUpdate = (props: IEducTypeUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { educTypeEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/educ-type');
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
        ...educTypeEntity,
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
          <h2 id="gbcResumeBankApp.educType.home.createOrEditLabel">
            <Translate contentKey="gbcResumeBankApp.educType.home.createOrEditLabel">Create or edit a EducType</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : educTypeEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="educ-type-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="educ-type-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="idEducTypeLabel" for="educ-type-idEducType">
                  <Translate contentKey="gbcResumeBankApp.educType.idEducType">Id Educ Type</Translate>
                </Label>
                <AvField id="educ-type-idEducType" type="string" className="form-control" name="idEducType" />
              </AvGroup>
              <AvGroup>
                <Label id="educTypeNmLabel" for="educ-type-educTypeNm">
                  <Translate contentKey="gbcResumeBankApp.educType.educTypeNm">Educ Type Nm</Translate>
                </Label>
                <AvField id="educ-type-educTypeNm" type="text" name="educTypeNm" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/educ-type" replace color="info">
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
  educTypeEntity: storeState.educType.entity,
  loading: storeState.educType.loading,
  updating: storeState.educType.updating,
  updateSuccess: storeState.educType.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EducTypeUpdate);

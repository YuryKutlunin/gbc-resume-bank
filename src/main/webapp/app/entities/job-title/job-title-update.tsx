import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './job-title.reducer';
import { IJobTitle } from 'app/shared/model/job-title.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IJobTitleUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const JobTitleUpdate = (props: IJobTitleUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { jobTitleEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/job-title');
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
        ...jobTitleEntity,
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
          <h2 id="gbcResumeBankApp.jobTitle.home.createOrEditLabel">
            <Translate contentKey="gbcResumeBankApp.jobTitle.home.createOrEditLabel">Create or edit a JobTitle</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : jobTitleEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="job-title-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="job-title-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="idTitleLabel" for="job-title-idTitle">
                  <Translate contentKey="gbcResumeBankApp.jobTitle.idTitle">Id Title</Translate>
                </Label>
                <AvField id="job-title-idTitle" type="string" className="form-control" name="idTitle" />
              </AvGroup>
              <AvGroup>
                <Label id="titleNMLabel" for="job-title-titleNM">
                  <Translate contentKey="gbcResumeBankApp.jobTitle.titleNM">Title NM</Translate>
                </Label>
                <AvField id="job-title-titleNM" type="text" name="titleNM" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/job-title" replace color="info">
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
  jobTitleEntity: storeState.jobTitle.entity,
  loading: storeState.jobTitle.loading,
  updating: storeState.jobTitle.updating,
  updateSuccess: storeState.jobTitle.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(JobTitleUpdate);

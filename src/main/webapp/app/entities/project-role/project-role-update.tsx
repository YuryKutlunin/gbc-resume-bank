import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './project-role.reducer';
import { IProjectRole } from 'app/shared/model/project-role.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IProjectRoleUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ProjectRoleUpdate = (props: IProjectRoleUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { projectRoleEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/project-role');
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
        ...projectRoleEntity,
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
          <h2 id="gbcResumeBankApp.projectRole.home.createOrEditLabel">
            <Translate contentKey="gbcResumeBankApp.projectRole.home.createOrEditLabel">Create or edit a ProjectRole</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : projectRoleEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="project-role-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="project-role-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="idRoleLabel" for="project-role-idRole">
                  <Translate contentKey="gbcResumeBankApp.projectRole.idRole">Id Role</Translate>
                </Label>
                <AvField id="project-role-idRole" type="string" className="form-control" name="idRole" />
              </AvGroup>
              <AvGroup>
                <Label id="roleNmLabel" for="project-role-roleNm">
                  <Translate contentKey="gbcResumeBankApp.projectRole.roleNm">Role Nm</Translate>
                </Label>
                <AvField id="project-role-roleNm" type="text" name="roleNm" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/project-role" replace color="info">
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
  projectRoleEntity: storeState.projectRole.entity,
  loading: storeState.projectRole.loading,
  updating: storeState.projectRole.updating,
  updateSuccess: storeState.projectRole.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ProjectRoleUpdate);

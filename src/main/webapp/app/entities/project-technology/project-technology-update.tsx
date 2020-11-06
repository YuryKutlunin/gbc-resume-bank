import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IProject } from 'app/shared/model/project.model';
import { getEntities as getProjects } from 'app/entities/project/project.reducer';
import { ITechnology } from 'app/shared/model/technology.model';
import { getEntities as getTechnologies } from 'app/entities/technology/technology.reducer';
import { getEntity, updateEntity, createEntity, reset } from './project-technology.reducer';
import { IProjectTechnology } from 'app/shared/model/project-technology.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IProjectTechnologyUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ProjectTechnologyUpdate = (props: IProjectTechnologyUpdateProps) => {
  const [idProjectId, setIdProjectId] = useState('0');
  const [idProjectId, setIdProjectId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { projectTechnologyEntity, projects, technologies, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/project-technology');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getProjects();
    props.getTechnologies();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...projectTechnologyEntity,
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
          <h2 id="gbcResumeBankApp.projectTechnology.home.createOrEditLabel">
            <Translate contentKey="gbcResumeBankApp.projectTechnology.home.createOrEditLabel">Create or edit a ProjectTechnology</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : projectTechnologyEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="project-technology-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="project-technology-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="idProjectLabel" for="project-technology-idProject">
                  <Translate contentKey="gbcResumeBankApp.projectTechnology.idProject">Id Project</Translate>
                </Label>
                <AvField id="project-technology-idProject" type="string" className="form-control" name="idProject" />
              </AvGroup>
              <AvGroup>
                <Label id="idTechnologyLabel" for="project-technology-idTechnology">
                  <Translate contentKey="gbcResumeBankApp.projectTechnology.idTechnology">Id Technology</Translate>
                </Label>
                <AvField id="project-technology-idTechnology" type="string" className="form-control" name="idTechnology" />
              </AvGroup>
              <AvGroup>
                <Label for="project-technology-idProject">
                  <Translate contentKey="gbcResumeBankApp.projectTechnology.idProject">Id Project</Translate>
                </Label>
                <AvInput id="project-technology-idProject" type="select" className="form-control" name="idProject.id">
                  <option value="" key="0" />
                  {projects
                    ? projects.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="project-technology-idProject">
                  <Translate contentKey="gbcResumeBankApp.projectTechnology.idProject">Id Project</Translate>
                </Label>
                <AvInput id="project-technology-idProject" type="select" className="form-control" name="idProject.id">
                  <option value="" key="0" />
                  {technologies
                    ? technologies.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/project-technology" replace color="info">
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
  projects: storeState.project.entities,
  technologies: storeState.technology.entities,
  projectTechnologyEntity: storeState.projectTechnology.entity,
  loading: storeState.projectTechnology.loading,
  updating: storeState.projectTechnology.updating,
  updateSuccess: storeState.projectTechnology.updateSuccess,
});

const mapDispatchToProps = {
  getProjects,
  getTechnologies,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ProjectTechnologyUpdate);

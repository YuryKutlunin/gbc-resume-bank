import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './project-technology.reducer';
import { IProjectTechnology } from 'app/shared/model/project-technology.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IProjectTechnologyDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ProjectTechnologyDetail = (props: IProjectTechnologyDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { projectTechnologyEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="gbcResumeBankApp.projectTechnology.detail.title">ProjectTechnology</Translate> [
          <b>{projectTechnologyEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="idProject">
              <Translate contentKey="gbcResumeBankApp.projectTechnology.idProject">Id Project</Translate>
            </span>
          </dt>
          <dd>{projectTechnologyEntity.idProject}</dd>
          <dt>
            <span id="idTechnology">
              <Translate contentKey="gbcResumeBankApp.projectTechnology.idTechnology">Id Technology</Translate>
            </span>
          </dt>
          <dd>{projectTechnologyEntity.idTechnology}</dd>
          <dt>
            <Translate contentKey="gbcResumeBankApp.projectTechnology.idProject">Id Project</Translate>
          </dt>
          <dd>{projectTechnologyEntity.idProject ? projectTechnologyEntity.idProject.id : ''}</dd>
          <dt>
            <Translate contentKey="gbcResumeBankApp.projectTechnology.idProject">Id Project</Translate>
          </dt>
          <dd>{projectTechnologyEntity.idProject ? projectTechnologyEntity.idProject.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/project-technology" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/project-technology/${projectTechnologyEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ projectTechnology }: IRootState) => ({
  projectTechnologyEntity: projectTechnology.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ProjectTechnologyDetail);

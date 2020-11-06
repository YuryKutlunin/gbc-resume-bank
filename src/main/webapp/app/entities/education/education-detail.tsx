import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './education.reducer';
import { IEducation } from 'app/shared/model/education.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEducationDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EducationDetail = (props: IEducationDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { educationEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="gbcResumeBankApp.education.detail.title">Education</Translate> [<b>{educationEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="email">
              <Translate contentKey="gbcResumeBankApp.education.email">Email</Translate>
            </span>
          </dt>
          <dd>{educationEntity.email}</dd>
          <dt>
            <span id="idUniver">
              <Translate contentKey="gbcResumeBankApp.education.idUniver">Id Univer</Translate>
            </span>
          </dt>
          <dd>{educationEntity.idUniver}</dd>
          <dt>
            <span id="idEducType">
              <Translate contentKey="gbcResumeBankApp.education.idEducType">Id Educ Type</Translate>
            </span>
          </dt>
          <dd>{educationEntity.idEducType}</dd>
          <dt>
            <span id="faculty">
              <Translate contentKey="gbcResumeBankApp.education.faculty">Faculty</Translate>
            </span>
          </dt>
          <dd>{educationEntity.faculty}</dd>
          <dt>
            <span id="specialty">
              <Translate contentKey="gbcResumeBankApp.education.specialty">Specialty</Translate>
            </span>
          </dt>
          <dd>{educationEntity.specialty}</dd>
          <dt>
            <span id="specialization">
              <Translate contentKey="gbcResumeBankApp.education.specialization">Specialization</Translate>
            </span>
          </dt>
          <dd>{educationEntity.specialization}</dd>
          <dt>
            <span id="startYear">
              <Translate contentKey="gbcResumeBankApp.education.startYear">Start Year</Translate>
            </span>
          </dt>
          <dd>{educationEntity.startYear}</dd>
          <dt>
            <span id="endYear">
              <Translate contentKey="gbcResumeBankApp.education.endYear">End Year</Translate>
            </span>
          </dt>
          <dd>{educationEntity.endYear}</dd>
          <dt>
            <Translate contentKey="gbcResumeBankApp.education.email">Email</Translate>
          </dt>
          <dd>{educationEntity.email ? educationEntity.email.id : ''}</dd>
          <dt>
            <Translate contentKey="gbcResumeBankApp.education.idUniver">Id Univer</Translate>
          </dt>
          <dd>{educationEntity.idUniver ? educationEntity.idUniver.id : ''}</dd>
          <dt>
            <Translate contentKey="gbcResumeBankApp.education.idEducType">Id Educ Type</Translate>
          </dt>
          <dd>{educationEntity.idEducType ? educationEntity.idEducType.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/education" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/education/${educationEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ education }: IRootState) => ({
  educationEntity: education.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EducationDetail);

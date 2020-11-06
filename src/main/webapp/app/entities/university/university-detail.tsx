import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './university.reducer';
import { IUniversity } from 'app/shared/model/university.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IUniversityDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const UniversityDetail = (props: IUniversityDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { universityEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="gbcResumeBankApp.university.detail.title">University</Translate> [<b>{universityEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="idUniver">
              <Translate contentKey="gbcResumeBankApp.university.idUniver">Id Univer</Translate>
            </span>
          </dt>
          <dd>{universityEntity.idUniver}</dd>
          <dt>
            <span id="univerNm">
              <Translate contentKey="gbcResumeBankApp.university.univerNm">Univer Nm</Translate>
            </span>
          </dt>
          <dd>{universityEntity.univerNm}</dd>
        </dl>
        <Button tag={Link} to="/university" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/university/${universityEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ university }: IRootState) => ({
  universityEntity: university.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(UniversityDetail);

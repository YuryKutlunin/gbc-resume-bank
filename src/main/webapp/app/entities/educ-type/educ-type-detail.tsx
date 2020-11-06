import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './educ-type.reducer';
import { IEducType } from 'app/shared/model/educ-type.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEducTypeDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EducTypeDetail = (props: IEducTypeDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { educTypeEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="gbcResumeBankApp.educType.detail.title">EducType</Translate> [<b>{educTypeEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="idEducType">
              <Translate contentKey="gbcResumeBankApp.educType.idEducType">Id Educ Type</Translate>
            </span>
          </dt>
          <dd>{educTypeEntity.idEducType}</dd>
          <dt>
            <span id="educTypeNm">
              <Translate contentKey="gbcResumeBankApp.educType.educTypeNm">Educ Type Nm</Translate>
            </span>
          </dt>
          <dd>{educTypeEntity.educTypeNm}</dd>
        </dl>
        <Button tag={Link} to="/educ-type" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/educ-type/${educTypeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ educType }: IRootState) => ({
  educTypeEntity: educType.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EducTypeDetail);

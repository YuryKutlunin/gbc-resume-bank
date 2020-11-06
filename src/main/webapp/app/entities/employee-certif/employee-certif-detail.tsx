import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './employee-certif.reducer';
import { IEmployeeCertif } from 'app/shared/model/employee-certif.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEmployeeCertifDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EmployeeCertifDetail = (props: IEmployeeCertifDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { employeeCertifEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="gbcResumeBankApp.employeeCertif.detail.title">EmployeeCertif</Translate> [<b>{employeeCertifEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="email">
              <Translate contentKey="gbcResumeBankApp.employeeCertif.email">Email</Translate>
            </span>
          </dt>
          <dd>{employeeCertifEntity.email}</dd>
          <dt>
            <span id="idCertificate">
              <Translate contentKey="gbcResumeBankApp.employeeCertif.idCertificate">Id Certificate</Translate>
            </span>
          </dt>
          <dd>{employeeCertifEntity.idCertificate}</dd>
          <dt>
            <span id="startDt">
              <Translate contentKey="gbcResumeBankApp.employeeCertif.startDt">Start Dt</Translate>
            </span>
          </dt>
          <dd>
            {employeeCertifEntity.startDt ? <TextFormat value={employeeCertifEntity.startDt} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="endDt">
              <Translate contentKey="gbcResumeBankApp.employeeCertif.endDt">End Dt</Translate>
            </span>
          </dt>
          <dd>
            {employeeCertifEntity.endDt ? <TextFormat value={employeeCertifEntity.endDt} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <Translate contentKey="gbcResumeBankApp.employeeCertif.email">Email</Translate>
          </dt>
          <dd>{employeeCertifEntity.email ? employeeCertifEntity.email.id : ''}</dd>
          <dt>
            <Translate contentKey="gbcResumeBankApp.employeeCertif.idCertificate">Id Certificate</Translate>
          </dt>
          <dd>{employeeCertifEntity.idCertificate ? employeeCertifEntity.idCertificate.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/employee-certif" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/employee-certif/${employeeCertifEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ employeeCertif }: IRootState) => ({
  employeeCertifEntity: employeeCertif.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EmployeeCertifDetail);

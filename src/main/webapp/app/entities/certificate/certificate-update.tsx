import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './certificate.reducer';
import { ICertificate } from 'app/shared/model/certificate.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ICertificateUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CertificateUpdate = (props: ICertificateUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { certificateEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/certificate');
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
        ...certificateEntity,
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
          <h2 id="gbcResumeBankApp.certificate.home.createOrEditLabel">
            <Translate contentKey="gbcResumeBankApp.certificate.home.createOrEditLabel">Create or edit a Certificate</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : certificateEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="certificate-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="certificate-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="idCertificateLabel" for="certificate-idCertificate">
                  <Translate contentKey="gbcResumeBankApp.certificate.idCertificate">Id Certificate</Translate>
                </Label>
                <AvField id="certificate-idCertificate" type="string" className="form-control" name="idCertificate" />
              </AvGroup>
              <AvGroup>
                <Label id="certificateNmLabel" for="certificate-certificateNm">
                  <Translate contentKey="gbcResumeBankApp.certificate.certificateNm">Certificate Nm</Translate>
                </Label>
                <AvField id="certificate-certificateNm" type="text" name="certificateNm" />
              </AvGroup>
              <AvGroup>
                <Label id="certScopeNmLabel" for="certificate-certScopeNm">
                  <Translate contentKey="gbcResumeBankApp.certificate.certScopeNm">Cert Scope Nm</Translate>
                </Label>
                <AvField id="certificate-certScopeNm" type="text" name="certScopeNm" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/certificate" replace color="info">
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
  certificateEntity: storeState.certificate.entity,
  loading: storeState.certificate.loading,
  updating: storeState.certificate.updating,
  updateSuccess: storeState.certificate.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CertificateUpdate);

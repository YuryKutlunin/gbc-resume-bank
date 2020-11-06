import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './employee.reducer';
import { IEmployee } from 'app/shared/model/employee.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEmployeeDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EmployeeDetail = (props: IEmployeeDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { employeeEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="gbcResumeBankApp.employee.detail.title">Employee</Translate> [<b>{employeeEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="firstNm">
              <Translate contentKey="gbcResumeBankApp.employee.firstNm">First Nm</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.firstNm}</dd>
          <dt>
            <span id="lastNm">
              <Translate contentKey="gbcResumeBankApp.employee.lastNm">Last Nm</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.lastNm}</dd>
          <dt>
            <span id="middleNm">
              <Translate contentKey="gbcResumeBankApp.employee.middleNm">Middle Nm</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.middleNm}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="gbcResumeBankApp.employee.email">Email</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.email}</dd>
          <dt>
            <span id="phoneNum">
              <Translate contentKey="gbcResumeBankApp.employee.phoneNum">Phone Num</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.phoneNum}</dd>
          <dt>
            <span id="workType">
              <Translate contentKey="gbcResumeBankApp.employee.workType">Work Type</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.workType}</dd>
          <dt>
            <span id="birthDt">
              <Translate contentKey="gbcResumeBankApp.employee.birthDt">Birth Dt</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.birthDt ? <TextFormat value={employeeEntity.birthDt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="idTitle">
              <Translate contentKey="gbcResumeBankApp.employee.idTitle">Id Title</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.idTitle}</dd>
          <dt>
            <span id="resourcePoolCode">
              <Translate contentKey="gbcResumeBankApp.employee.resourcePoolCode">Resource Pool Code</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.resourcePoolCode}</dd>
          <dt>
            <span id="emailCurator">
              <Translate contentKey="gbcResumeBankApp.employee.emailCurator">Email Curator</Translate>
            </span>
          </dt>
          <dd>{employeeEntity.emailCurator}</dd>
          <dt>
            <Translate contentKey="gbcResumeBankApp.employee.email">Email</Translate>
          </dt>
          <dd>{employeeEntity.email ? employeeEntity.email.id : ''}</dd>
          <dt>
            <Translate contentKey="gbcResumeBankApp.employee.idResourcePool">Id Resource Pool</Translate>
          </dt>
          <dd>{employeeEntity.idResourcePool ? employeeEntity.idResourcePool.id : ''}</dd>
          <dt>
            <Translate contentKey="gbcResumeBankApp.employee.idTitle">Id Title</Translate>
          </dt>
          <dd>{employeeEntity.idTitle ? employeeEntity.idTitle.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/employee" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/employee/${employeeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ employee }: IRootState) => ({
  employeeEntity: employee.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EmployeeDetail);

import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './employee-skill.reducer';
import { IEmployeeSkill } from 'app/shared/model/employee-skill.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEmployeeSkillDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EmployeeSkillDetail = (props: IEmployeeSkillDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { employeeSkillEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="gbcResumeBankApp.employeeSkill.detail.title">EmployeeSkill</Translate> [<b>{employeeSkillEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="email">
              <Translate contentKey="gbcResumeBankApp.employeeSkill.email">Email</Translate>
            </span>
          </dt>
          <dd>{employeeSkillEntity.email}</dd>
          <dt>
            <span id="idSkill">
              <Translate contentKey="gbcResumeBankApp.employeeSkill.idSkill">Id Skill</Translate>
            </span>
          </dt>
          <dd>{employeeSkillEntity.idSkill}</dd>
          <dt>
            <span id="idLevel">
              <Translate contentKey="gbcResumeBankApp.employeeSkill.idLevel">Id Level</Translate>
            </span>
          </dt>
          <dd>{employeeSkillEntity.idLevel}</dd>
          <dt>
            <Translate contentKey="gbcResumeBankApp.employeeSkill.email">Email</Translate>
          </dt>
          <dd>{employeeSkillEntity.email ? employeeSkillEntity.email.id : ''}</dd>
          <dt>
            <Translate contentKey="gbcResumeBankApp.employeeSkill.idSkill">Id Skill</Translate>
          </dt>
          <dd>{employeeSkillEntity.idSkill ? employeeSkillEntity.idSkill.id : ''}</dd>
          <dt>
            <Translate contentKey="gbcResumeBankApp.employeeSkill.idLevel">Id Level</Translate>
          </dt>
          <dd>{employeeSkillEntity.idLevel ? employeeSkillEntity.idLevel.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/employee-skill" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/employee-skill/${employeeSkillEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ employeeSkill }: IRootState) => ({
  employeeSkillEntity: employeeSkill.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EmployeeSkillDetail);

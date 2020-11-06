import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IEmployee } from 'app/shared/model/employee.model';
import { getEntities as getEmployees } from 'app/entities/employee/employee.reducer';
import { ISkill } from 'app/shared/model/skill.model';
import { getEntities as getSkills } from 'app/entities/skill/skill.reducer';
import { ISkillLevel } from 'app/shared/model/skill-level.model';
import { getEntities as getSkillLevels } from 'app/entities/skill-level/skill-level.reducer';
import { getEntity, updateEntity, createEntity, reset } from './employee-skill.reducer';
import { IEmployeeSkill } from 'app/shared/model/employee-skill.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IEmployeeSkillUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EmployeeSkillUpdate = (props: IEmployeeSkillUpdateProps) => {
  const [emailId, setEmailId] = useState('0');
  const [idSkillId, setIdSkillId] = useState('0');
  const [idLevelId, setIdLevelId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { employeeSkillEntity, employees, skills, skillLevels, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/employee-skill');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getEmployees();
    props.getSkills();
    props.getSkillLevels();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...employeeSkillEntity,
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
          <h2 id="gbcResumeBankApp.employeeSkill.home.createOrEditLabel">
            <Translate contentKey="gbcResumeBankApp.employeeSkill.home.createOrEditLabel">Create or edit a EmployeeSkill</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : employeeSkillEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="employee-skill-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="employee-skill-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="emailLabel" for="employee-skill-email">
                  <Translate contentKey="gbcResumeBankApp.employeeSkill.email">Email</Translate>
                </Label>
                <AvField id="employee-skill-email" type="text" name="email" />
              </AvGroup>
              <AvGroup>
                <Label id="idSkillLabel" for="employee-skill-idSkill">
                  <Translate contentKey="gbcResumeBankApp.employeeSkill.idSkill">Id Skill</Translate>
                </Label>
                <AvField id="employee-skill-idSkill" type="string" className="form-control" name="idSkill" />
              </AvGroup>
              <AvGroup>
                <Label id="idLevelLabel" for="employee-skill-idLevel">
                  <Translate contentKey="gbcResumeBankApp.employeeSkill.idLevel">Id Level</Translate>
                </Label>
                <AvField id="employee-skill-idLevel" type="string" className="form-control" name="idLevel" />
              </AvGroup>
              <AvGroup>
                <Label for="employee-skill-email">
                  <Translate contentKey="gbcResumeBankApp.employeeSkill.email">Email</Translate>
                </Label>
                <AvInput id="employee-skill-email" type="select" className="form-control" name="email.id">
                  <option value="" key="0" />
                  {employees
                    ? employees.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="employee-skill-idSkill">
                  <Translate contentKey="gbcResumeBankApp.employeeSkill.idSkill">Id Skill</Translate>
                </Label>
                <AvInput id="employee-skill-idSkill" type="select" className="form-control" name="idSkill.id">
                  <option value="" key="0" />
                  {skills
                    ? skills.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="employee-skill-idLevel">
                  <Translate contentKey="gbcResumeBankApp.employeeSkill.idLevel">Id Level</Translate>
                </Label>
                <AvInput id="employee-skill-idLevel" type="select" className="form-control" name="idLevel.id">
                  <option value="" key="0" />
                  {skillLevels
                    ? skillLevels.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/employee-skill" replace color="info">
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
  employees: storeState.employee.entities,
  skills: storeState.skill.entities,
  skillLevels: storeState.skillLevel.entities,
  employeeSkillEntity: storeState.employeeSkill.entity,
  loading: storeState.employeeSkill.loading,
  updating: storeState.employeeSkill.updating,
  updateSuccess: storeState.employeeSkill.updateSuccess,
});

const mapDispatchToProps = {
  getEmployees,
  getSkills,
  getSkillLevels,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EmployeeSkillUpdate);

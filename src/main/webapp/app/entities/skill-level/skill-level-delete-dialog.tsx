import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { RouteComponentProps } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';
import { Translate, ICrudGetAction, ICrudDeleteAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { ISkillLevel } from 'app/shared/model/skill-level.model';
import { IRootState } from 'app/shared/reducers';
import { getEntity, deleteEntity } from './skill-level.reducer';

export interface ISkillLevelDeleteDialogProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const SkillLevelDeleteDialog = (props: ISkillLevelDeleteDialogProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const handleClose = () => {
    props.history.push('/skill-level');
  };

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const confirmDelete = () => {
    props.deleteEntity(props.skillLevelEntity.id);
  };

  const { skillLevelEntity } = props;
  return (
    <Modal isOpen toggle={handleClose}>
      <ModalHeader toggle={handleClose}>
        <Translate contentKey="entity.delete.title">Confirm delete operation</Translate>
      </ModalHeader>
      <ModalBody id="gbcResumeBankApp.skillLevel.delete.question">
        <Translate contentKey="gbcResumeBankApp.skillLevel.delete.question" interpolate={{ id: skillLevelEntity.id }}>
          Are you sure you want to delete this SkillLevel?
        </Translate>
      </ModalBody>
      <ModalFooter>
        <Button color="secondary" onClick={handleClose}>
          <FontAwesomeIcon icon="ban" />
          &nbsp;
          <Translate contentKey="entity.action.cancel">Cancel</Translate>
        </Button>
        <Button id="jhi-confirm-delete-skillLevel" color="danger" onClick={confirmDelete}>
          <FontAwesomeIcon icon="trash" />
          &nbsp;
          <Translate contentKey="entity.action.delete">Delete</Translate>
        </Button>
      </ModalFooter>
    </Modal>
  );
};

const mapStateToProps = ({ skillLevel }: IRootState) => ({
  skillLevelEntity: skillLevel.entity,
  updateSuccess: skillLevel.updateSuccess,
});

const mapDispatchToProps = { getEntity, deleteEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(SkillLevelDeleteDialog);

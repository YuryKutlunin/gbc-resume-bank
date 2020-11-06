import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './employee-certif.reducer';
import { IEmployeeCertif } from 'app/shared/model/employee-certif.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEmployeeCertifProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const EmployeeCertif = (props: IEmployeeCertifProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { employeeCertifList, match, loading } = props;
  return (
    <div>
      <h2 id="employee-certif-heading">
        <Translate contentKey="gbcResumeBankApp.employeeCertif.home.title">Employee Certifs</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="gbcResumeBankApp.employeeCertif.home.createLabel">Create new Employee Certif</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {employeeCertifList && employeeCertifList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="gbcResumeBankApp.employeeCertif.email">Email</Translate>
                </th>
                <th>
                  <Translate contentKey="gbcResumeBankApp.employeeCertif.idCertificate">Id Certificate</Translate>
                </th>
                <th>
                  <Translate contentKey="gbcResumeBankApp.employeeCertif.startDt">Start Dt</Translate>
                </th>
                <th>
                  <Translate contentKey="gbcResumeBankApp.employeeCertif.endDt">End Dt</Translate>
                </th>
                <th>
                  <Translate contentKey="gbcResumeBankApp.employeeCertif.email">Email</Translate>
                </th>
                <th>
                  <Translate contentKey="gbcResumeBankApp.employeeCertif.idCertificate">Id Certificate</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {employeeCertifList.map((employeeCertif, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${employeeCertif.id}`} color="link" size="sm">
                      {employeeCertif.id}
                    </Button>
                  </td>
                  <td>{employeeCertif.email}</td>
                  <td>{employeeCertif.idCertificate}</td>
                  <td>
                    {employeeCertif.startDt ? <TextFormat type="date" value={employeeCertif.startDt} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>{employeeCertif.endDt ? <TextFormat type="date" value={employeeCertif.endDt} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{employeeCertif.email ? <Link to={`employee/${employeeCertif.email.id}`}>{employeeCertif.email.id}</Link> : ''}</td>
                  <td>
                    {employeeCertif.idCertificate ? (
                      <Link to={`certificate/${employeeCertif.idCertificate.id}`}>{employeeCertif.idCertificate.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${employeeCertif.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${employeeCertif.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${employeeCertif.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="gbcResumeBankApp.employeeCertif.home.notFound">No Employee Certifs found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ employeeCertif }: IRootState) => ({
  employeeCertifList: employeeCertif.entities,
  loading: employeeCertif.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EmployeeCertif);

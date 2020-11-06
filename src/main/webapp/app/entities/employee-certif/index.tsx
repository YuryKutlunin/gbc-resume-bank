import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import EmployeeCertif from './employee-certif';
import EmployeeCertifDetail from './employee-certif-detail';
import EmployeeCertifUpdate from './employee-certif-update';
import EmployeeCertifDeleteDialog from './employee-certif-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={EmployeeCertifUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={EmployeeCertifUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={EmployeeCertifDetail} />
      <ErrorBoundaryRoute path={match.url} component={EmployeeCertif} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={EmployeeCertifDeleteDialog} />
  </>
);

export default Routes;

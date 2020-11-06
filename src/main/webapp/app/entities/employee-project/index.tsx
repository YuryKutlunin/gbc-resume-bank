import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import EmployeeProject from './employee-project';
import EmployeeProjectDetail from './employee-project-detail';
import EmployeeProjectUpdate from './employee-project-update';
import EmployeeProjectDeleteDialog from './employee-project-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={EmployeeProjectUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={EmployeeProjectUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={EmployeeProjectDetail} />
      <ErrorBoundaryRoute path={match.url} component={EmployeeProject} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={EmployeeProjectDeleteDialog} />
  </>
);

export default Routes;

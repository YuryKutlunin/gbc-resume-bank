import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ProjectRole from './project-role';
import ProjectRoleDetail from './project-role-detail';
import ProjectRoleUpdate from './project-role-update';
import ProjectRoleDeleteDialog from './project-role-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ProjectRoleUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ProjectRoleUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ProjectRoleDetail} />
      <ErrorBoundaryRoute path={match.url} component={ProjectRole} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ProjectRoleDeleteDialog} />
  </>
);

export default Routes;

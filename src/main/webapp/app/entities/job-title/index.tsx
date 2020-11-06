import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import JobTitle from './job-title';
import JobTitleDetail from './job-title-detail';
import JobTitleUpdate from './job-title-update';
import JobTitleDeleteDialog from './job-title-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={JobTitleUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={JobTitleUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={JobTitleDetail} />
      <ErrorBoundaryRoute path={match.url} component={JobTitle} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={JobTitleDeleteDialog} />
  </>
);

export default Routes;

import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ProjectTechnology from './project-technology';
import ProjectTechnologyDetail from './project-technology-detail';
import ProjectTechnologyUpdate from './project-technology-update';
import ProjectTechnologyDeleteDialog from './project-technology-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ProjectTechnologyUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ProjectTechnologyUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ProjectTechnologyDetail} />
      <ErrorBoundaryRoute path={match.url} component={ProjectTechnology} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ProjectTechnologyDeleteDialog} />
  </>
);

export default Routes;

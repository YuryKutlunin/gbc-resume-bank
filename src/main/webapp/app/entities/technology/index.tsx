import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Technology from './technology';
import TechnologyDetail from './technology-detail';
import TechnologyUpdate from './technology-update';
import TechnologyDeleteDialog from './technology-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TechnologyUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TechnologyUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TechnologyDetail} />
      <ErrorBoundaryRoute path={match.url} component={Technology} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={TechnologyDeleteDialog} />
  </>
);

export default Routes;

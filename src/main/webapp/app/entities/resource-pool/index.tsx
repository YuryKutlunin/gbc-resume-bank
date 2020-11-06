import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ResourcePool from './resource-pool';
import ResourcePoolDetail from './resource-pool-detail';
import ResourcePoolUpdate from './resource-pool-update';
import ResourcePoolDeleteDialog from './resource-pool-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ResourcePoolUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ResourcePoolUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ResourcePoolDetail} />
      <ErrorBoundaryRoute path={match.url} component={ResourcePool} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ResourcePoolDeleteDialog} />
  </>
);

export default Routes;

import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import EducType from './educ-type';
import EducTypeDetail from './educ-type-detail';
import EducTypeUpdate from './educ-type-update';
import EducTypeDeleteDialog from './educ-type-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={EducTypeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={EducTypeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={EducTypeDetail} />
      <ErrorBoundaryRoute path={match.url} component={EducType} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={EducTypeDeleteDialog} />
  </>
);

export default Routes;

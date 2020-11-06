import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IProjectRole, defaultValue } from 'app/shared/model/project-role.model';

export const ACTION_TYPES = {
  FETCH_PROJECTROLE_LIST: 'projectRole/FETCH_PROJECTROLE_LIST',
  FETCH_PROJECTROLE: 'projectRole/FETCH_PROJECTROLE',
  CREATE_PROJECTROLE: 'projectRole/CREATE_PROJECTROLE',
  UPDATE_PROJECTROLE: 'projectRole/UPDATE_PROJECTROLE',
  DELETE_PROJECTROLE: 'projectRole/DELETE_PROJECTROLE',
  RESET: 'projectRole/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IProjectRole>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type ProjectRoleState = Readonly<typeof initialState>;

// Reducer

export default (state: ProjectRoleState = initialState, action): ProjectRoleState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_PROJECTROLE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PROJECTROLE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_PROJECTROLE):
    case REQUEST(ACTION_TYPES.UPDATE_PROJECTROLE):
    case REQUEST(ACTION_TYPES.DELETE_PROJECTROLE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_PROJECTROLE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PROJECTROLE):
    case FAILURE(ACTION_TYPES.CREATE_PROJECTROLE):
    case FAILURE(ACTION_TYPES.UPDATE_PROJECTROLE):
    case FAILURE(ACTION_TYPES.DELETE_PROJECTROLE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_PROJECTROLE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_PROJECTROLE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_PROJECTROLE):
    case SUCCESS(ACTION_TYPES.UPDATE_PROJECTROLE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_PROJECTROLE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/project-roles';

// Actions

export const getEntities: ICrudGetAllAction<IProjectRole> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_PROJECTROLE_LIST,
  payload: axios.get<IProjectRole>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IProjectRole> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PROJECTROLE,
    payload: axios.get<IProjectRole>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IProjectRole> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PROJECTROLE,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IProjectRole> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PROJECTROLE,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IProjectRole> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PROJECTROLE,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});

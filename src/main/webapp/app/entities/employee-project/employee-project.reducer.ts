import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IEmployeeProject, defaultValue } from 'app/shared/model/employee-project.model';

export const ACTION_TYPES = {
  FETCH_EMPLOYEEPROJECT_LIST: 'employeeProject/FETCH_EMPLOYEEPROJECT_LIST',
  FETCH_EMPLOYEEPROJECT: 'employeeProject/FETCH_EMPLOYEEPROJECT',
  CREATE_EMPLOYEEPROJECT: 'employeeProject/CREATE_EMPLOYEEPROJECT',
  UPDATE_EMPLOYEEPROJECT: 'employeeProject/UPDATE_EMPLOYEEPROJECT',
  DELETE_EMPLOYEEPROJECT: 'employeeProject/DELETE_EMPLOYEEPROJECT',
  RESET: 'employeeProject/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IEmployeeProject>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type EmployeeProjectState = Readonly<typeof initialState>;

// Reducer

export default (state: EmployeeProjectState = initialState, action): EmployeeProjectState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_EMPLOYEEPROJECT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_EMPLOYEEPROJECT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_EMPLOYEEPROJECT):
    case REQUEST(ACTION_TYPES.UPDATE_EMPLOYEEPROJECT):
    case REQUEST(ACTION_TYPES.DELETE_EMPLOYEEPROJECT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_EMPLOYEEPROJECT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_EMPLOYEEPROJECT):
    case FAILURE(ACTION_TYPES.CREATE_EMPLOYEEPROJECT):
    case FAILURE(ACTION_TYPES.UPDATE_EMPLOYEEPROJECT):
    case FAILURE(ACTION_TYPES.DELETE_EMPLOYEEPROJECT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_EMPLOYEEPROJECT_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_EMPLOYEEPROJECT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_EMPLOYEEPROJECT):
    case SUCCESS(ACTION_TYPES.UPDATE_EMPLOYEEPROJECT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_EMPLOYEEPROJECT):
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

const apiUrl = 'api/employee-projects';

// Actions

export const getEntities: ICrudGetAllAction<IEmployeeProject> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_EMPLOYEEPROJECT_LIST,
  payload: axios.get<IEmployeeProject>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IEmployeeProject> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_EMPLOYEEPROJECT,
    payload: axios.get<IEmployeeProject>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IEmployeeProject> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_EMPLOYEEPROJECT,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IEmployeeProject> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_EMPLOYEEPROJECT,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IEmployeeProject> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_EMPLOYEEPROJECT,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});

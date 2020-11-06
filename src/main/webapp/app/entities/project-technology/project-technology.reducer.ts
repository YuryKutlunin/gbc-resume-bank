import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IProjectTechnology, defaultValue } from 'app/shared/model/project-technology.model';

export const ACTION_TYPES = {
  FETCH_PROJECTTECHNOLOGY_LIST: 'projectTechnology/FETCH_PROJECTTECHNOLOGY_LIST',
  FETCH_PROJECTTECHNOLOGY: 'projectTechnology/FETCH_PROJECTTECHNOLOGY',
  CREATE_PROJECTTECHNOLOGY: 'projectTechnology/CREATE_PROJECTTECHNOLOGY',
  UPDATE_PROJECTTECHNOLOGY: 'projectTechnology/UPDATE_PROJECTTECHNOLOGY',
  DELETE_PROJECTTECHNOLOGY: 'projectTechnology/DELETE_PROJECTTECHNOLOGY',
  RESET: 'projectTechnology/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IProjectTechnology>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type ProjectTechnologyState = Readonly<typeof initialState>;

// Reducer

export default (state: ProjectTechnologyState = initialState, action): ProjectTechnologyState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_PROJECTTECHNOLOGY_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PROJECTTECHNOLOGY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_PROJECTTECHNOLOGY):
    case REQUEST(ACTION_TYPES.UPDATE_PROJECTTECHNOLOGY):
    case REQUEST(ACTION_TYPES.DELETE_PROJECTTECHNOLOGY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_PROJECTTECHNOLOGY_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PROJECTTECHNOLOGY):
    case FAILURE(ACTION_TYPES.CREATE_PROJECTTECHNOLOGY):
    case FAILURE(ACTION_TYPES.UPDATE_PROJECTTECHNOLOGY):
    case FAILURE(ACTION_TYPES.DELETE_PROJECTTECHNOLOGY):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_PROJECTTECHNOLOGY_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_PROJECTTECHNOLOGY):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_PROJECTTECHNOLOGY):
    case SUCCESS(ACTION_TYPES.UPDATE_PROJECTTECHNOLOGY):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_PROJECTTECHNOLOGY):
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

const apiUrl = 'api/project-technologies';

// Actions

export const getEntities: ICrudGetAllAction<IProjectTechnology> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_PROJECTTECHNOLOGY_LIST,
  payload: axios.get<IProjectTechnology>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IProjectTechnology> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PROJECTTECHNOLOGY,
    payload: axios.get<IProjectTechnology>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IProjectTechnology> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PROJECTTECHNOLOGY,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IProjectTechnology> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PROJECTTECHNOLOGY,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IProjectTechnology> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PROJECTTECHNOLOGY,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});

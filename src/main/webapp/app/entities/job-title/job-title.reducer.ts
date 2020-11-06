import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IJobTitle, defaultValue } from 'app/shared/model/job-title.model';

export const ACTION_TYPES = {
  FETCH_JOBTITLE_LIST: 'jobTitle/FETCH_JOBTITLE_LIST',
  FETCH_JOBTITLE: 'jobTitle/FETCH_JOBTITLE',
  CREATE_JOBTITLE: 'jobTitle/CREATE_JOBTITLE',
  UPDATE_JOBTITLE: 'jobTitle/UPDATE_JOBTITLE',
  DELETE_JOBTITLE: 'jobTitle/DELETE_JOBTITLE',
  RESET: 'jobTitle/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IJobTitle>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type JobTitleState = Readonly<typeof initialState>;

// Reducer

export default (state: JobTitleState = initialState, action): JobTitleState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_JOBTITLE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_JOBTITLE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_JOBTITLE):
    case REQUEST(ACTION_TYPES.UPDATE_JOBTITLE):
    case REQUEST(ACTION_TYPES.DELETE_JOBTITLE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_JOBTITLE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_JOBTITLE):
    case FAILURE(ACTION_TYPES.CREATE_JOBTITLE):
    case FAILURE(ACTION_TYPES.UPDATE_JOBTITLE):
    case FAILURE(ACTION_TYPES.DELETE_JOBTITLE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_JOBTITLE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_JOBTITLE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_JOBTITLE):
    case SUCCESS(ACTION_TYPES.UPDATE_JOBTITLE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_JOBTITLE):
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

const apiUrl = 'api/job-titles';

// Actions

export const getEntities: ICrudGetAllAction<IJobTitle> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_JOBTITLE_LIST,
  payload: axios.get<IJobTitle>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IJobTitle> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_JOBTITLE,
    payload: axios.get<IJobTitle>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IJobTitle> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_JOBTITLE,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IJobTitle> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_JOBTITLE,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IJobTitle> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_JOBTITLE,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});

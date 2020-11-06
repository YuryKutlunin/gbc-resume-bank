import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IUniversity, defaultValue } from 'app/shared/model/university.model';

export const ACTION_TYPES = {
  FETCH_UNIVERSITY_LIST: 'university/FETCH_UNIVERSITY_LIST',
  FETCH_UNIVERSITY: 'university/FETCH_UNIVERSITY',
  CREATE_UNIVERSITY: 'university/CREATE_UNIVERSITY',
  UPDATE_UNIVERSITY: 'university/UPDATE_UNIVERSITY',
  DELETE_UNIVERSITY: 'university/DELETE_UNIVERSITY',
  RESET: 'university/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IUniversity>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type UniversityState = Readonly<typeof initialState>;

// Reducer

export default (state: UniversityState = initialState, action): UniversityState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_UNIVERSITY_LIST):
    case REQUEST(ACTION_TYPES.FETCH_UNIVERSITY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_UNIVERSITY):
    case REQUEST(ACTION_TYPES.UPDATE_UNIVERSITY):
    case REQUEST(ACTION_TYPES.DELETE_UNIVERSITY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_UNIVERSITY_LIST):
    case FAILURE(ACTION_TYPES.FETCH_UNIVERSITY):
    case FAILURE(ACTION_TYPES.CREATE_UNIVERSITY):
    case FAILURE(ACTION_TYPES.UPDATE_UNIVERSITY):
    case FAILURE(ACTION_TYPES.DELETE_UNIVERSITY):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_UNIVERSITY_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_UNIVERSITY):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_UNIVERSITY):
    case SUCCESS(ACTION_TYPES.UPDATE_UNIVERSITY):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_UNIVERSITY):
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

const apiUrl = 'api/universities';

// Actions

export const getEntities: ICrudGetAllAction<IUniversity> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_UNIVERSITY_LIST,
  payload: axios.get<IUniversity>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IUniversity> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_UNIVERSITY,
    payload: axios.get<IUniversity>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IUniversity> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_UNIVERSITY,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IUniversity> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_UNIVERSITY,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IUniversity> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_UNIVERSITY,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});

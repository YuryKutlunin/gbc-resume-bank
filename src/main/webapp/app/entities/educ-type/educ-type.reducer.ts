import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IEducType, defaultValue } from 'app/shared/model/educ-type.model';

export const ACTION_TYPES = {
  FETCH_EDUCTYPE_LIST: 'educType/FETCH_EDUCTYPE_LIST',
  FETCH_EDUCTYPE: 'educType/FETCH_EDUCTYPE',
  CREATE_EDUCTYPE: 'educType/CREATE_EDUCTYPE',
  UPDATE_EDUCTYPE: 'educType/UPDATE_EDUCTYPE',
  DELETE_EDUCTYPE: 'educType/DELETE_EDUCTYPE',
  RESET: 'educType/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IEducType>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type EducTypeState = Readonly<typeof initialState>;

// Reducer

export default (state: EducTypeState = initialState, action): EducTypeState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_EDUCTYPE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_EDUCTYPE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_EDUCTYPE):
    case REQUEST(ACTION_TYPES.UPDATE_EDUCTYPE):
    case REQUEST(ACTION_TYPES.DELETE_EDUCTYPE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_EDUCTYPE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_EDUCTYPE):
    case FAILURE(ACTION_TYPES.CREATE_EDUCTYPE):
    case FAILURE(ACTION_TYPES.UPDATE_EDUCTYPE):
    case FAILURE(ACTION_TYPES.DELETE_EDUCTYPE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_EDUCTYPE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_EDUCTYPE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_EDUCTYPE):
    case SUCCESS(ACTION_TYPES.UPDATE_EDUCTYPE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_EDUCTYPE):
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

const apiUrl = 'api/educ-types';

// Actions

export const getEntities: ICrudGetAllAction<IEducType> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_EDUCTYPE_LIST,
  payload: axios.get<IEducType>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IEducType> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_EDUCTYPE,
    payload: axios.get<IEducType>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IEducType> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_EDUCTYPE,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IEducType> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_EDUCTYPE,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IEducType> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_EDUCTYPE,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});

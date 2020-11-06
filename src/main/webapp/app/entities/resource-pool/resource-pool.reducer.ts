import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IResourcePool, defaultValue } from 'app/shared/model/resource-pool.model';

export const ACTION_TYPES = {
  FETCH_RESOURCEPOOL_LIST: 'resourcePool/FETCH_RESOURCEPOOL_LIST',
  FETCH_RESOURCEPOOL: 'resourcePool/FETCH_RESOURCEPOOL',
  CREATE_RESOURCEPOOL: 'resourcePool/CREATE_RESOURCEPOOL',
  UPDATE_RESOURCEPOOL: 'resourcePool/UPDATE_RESOURCEPOOL',
  DELETE_RESOURCEPOOL: 'resourcePool/DELETE_RESOURCEPOOL',
  RESET: 'resourcePool/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IResourcePool>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type ResourcePoolState = Readonly<typeof initialState>;

// Reducer

export default (state: ResourcePoolState = initialState, action): ResourcePoolState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_RESOURCEPOOL_LIST):
    case REQUEST(ACTION_TYPES.FETCH_RESOURCEPOOL):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_RESOURCEPOOL):
    case REQUEST(ACTION_TYPES.UPDATE_RESOURCEPOOL):
    case REQUEST(ACTION_TYPES.DELETE_RESOURCEPOOL):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_RESOURCEPOOL_LIST):
    case FAILURE(ACTION_TYPES.FETCH_RESOURCEPOOL):
    case FAILURE(ACTION_TYPES.CREATE_RESOURCEPOOL):
    case FAILURE(ACTION_TYPES.UPDATE_RESOURCEPOOL):
    case FAILURE(ACTION_TYPES.DELETE_RESOURCEPOOL):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_RESOURCEPOOL_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_RESOURCEPOOL):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_RESOURCEPOOL):
    case SUCCESS(ACTION_TYPES.UPDATE_RESOURCEPOOL):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_RESOURCEPOOL):
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

const apiUrl = 'api/resource-pools';

// Actions

export const getEntities: ICrudGetAllAction<IResourcePool> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_RESOURCEPOOL_LIST,
  payload: axios.get<IResourcePool>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IResourcePool> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_RESOURCEPOOL,
    payload: axios.get<IResourcePool>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IResourcePool> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_RESOURCEPOOL,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IResourcePool> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_RESOURCEPOOL,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IResourcePool> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_RESOURCEPOOL,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});

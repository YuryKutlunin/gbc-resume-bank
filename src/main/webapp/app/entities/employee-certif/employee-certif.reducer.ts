import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IEmployeeCertif, defaultValue } from 'app/shared/model/employee-certif.model';

export const ACTION_TYPES = {
  FETCH_EMPLOYEECERTIF_LIST: 'employeeCertif/FETCH_EMPLOYEECERTIF_LIST',
  FETCH_EMPLOYEECERTIF: 'employeeCertif/FETCH_EMPLOYEECERTIF',
  CREATE_EMPLOYEECERTIF: 'employeeCertif/CREATE_EMPLOYEECERTIF',
  UPDATE_EMPLOYEECERTIF: 'employeeCertif/UPDATE_EMPLOYEECERTIF',
  DELETE_EMPLOYEECERTIF: 'employeeCertif/DELETE_EMPLOYEECERTIF',
  RESET: 'employeeCertif/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IEmployeeCertif>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type EmployeeCertifState = Readonly<typeof initialState>;

// Reducer

export default (state: EmployeeCertifState = initialState, action): EmployeeCertifState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_EMPLOYEECERTIF_LIST):
    case REQUEST(ACTION_TYPES.FETCH_EMPLOYEECERTIF):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_EMPLOYEECERTIF):
    case REQUEST(ACTION_TYPES.UPDATE_EMPLOYEECERTIF):
    case REQUEST(ACTION_TYPES.DELETE_EMPLOYEECERTIF):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_EMPLOYEECERTIF_LIST):
    case FAILURE(ACTION_TYPES.FETCH_EMPLOYEECERTIF):
    case FAILURE(ACTION_TYPES.CREATE_EMPLOYEECERTIF):
    case FAILURE(ACTION_TYPES.UPDATE_EMPLOYEECERTIF):
    case FAILURE(ACTION_TYPES.DELETE_EMPLOYEECERTIF):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_EMPLOYEECERTIF_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_EMPLOYEECERTIF):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_EMPLOYEECERTIF):
    case SUCCESS(ACTION_TYPES.UPDATE_EMPLOYEECERTIF):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_EMPLOYEECERTIF):
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

const apiUrl = 'api/employee-certifs';

// Actions

export const getEntities: ICrudGetAllAction<IEmployeeCertif> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_EMPLOYEECERTIF_LIST,
  payload: axios.get<IEmployeeCertif>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IEmployeeCertif> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_EMPLOYEECERTIF,
    payload: axios.get<IEmployeeCertif>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IEmployeeCertif> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_EMPLOYEECERTIF,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IEmployeeCertif> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_EMPLOYEECERTIF,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IEmployeeCertif> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_EMPLOYEECERTIF,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});

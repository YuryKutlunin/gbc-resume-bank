import { IEmployee } from 'app/shared/model/employee.model';

export interface IResourcePool {
  id?: number;
  resourcePoolCode?: string;
  resourcePoolNm?: string;
  poolLeader?: string;
  idResourcePools?: IEmployee[];
}

export const defaultValue: Readonly<IResourcePool> = {};

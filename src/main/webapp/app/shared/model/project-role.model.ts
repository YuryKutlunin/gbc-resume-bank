import { IEmployeeProject } from 'app/shared/model/employee-project.model';

export interface IProjectRole {
  id?: number;
  idRole?: number;
  roleNm?: string;
  idRoles?: IEmployeeProject[];
}

export const defaultValue: Readonly<IProjectRole> = {};

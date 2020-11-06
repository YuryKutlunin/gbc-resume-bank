import { Moment } from 'moment';
import { IEmployee } from 'app/shared/model/employee.model';
import { IProject } from 'app/shared/model/project.model';
import { IProjectRole } from 'app/shared/model/project-role.model';

export interface IEmployeeProject {
  id?: number;
  email?: string;
  idProject?: number;
  idRole?: number;
  responsibilityNm?: string;
  startDt?: string;
  endDt?: string;
  email?: IEmployee;
  idProject?: IProject;
  idRole?: IProjectRole;
}

export const defaultValue: Readonly<IEmployeeProject> = {};

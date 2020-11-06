import { Moment } from 'moment';
import { IProjectTechnology } from 'app/shared/model/project-technology.model';
import { IEmployeeProject } from 'app/shared/model/employee-project.model';

export interface IProject {
  id?: number;
  idProject?: number;
  projectNm?: string;
  companyNM?: string;
  startDt?: string;
  endDt?: string;
  idProjects?: IProjectTechnology[];
  idProjects?: IEmployeeProject[];
}

export const defaultValue: Readonly<IProject> = {};

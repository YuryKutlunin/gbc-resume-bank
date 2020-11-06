import { IEmployee } from 'app/shared/model/employee.model';

export interface IJobTitle {
  id?: number;
  idTitle?: number;
  titleNM?: string;
  idTitles?: IEmployee[];
}

export const defaultValue: Readonly<IJobTitle> = {};

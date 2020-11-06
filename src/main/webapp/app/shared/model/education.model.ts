import { IEmployee } from 'app/shared/model/employee.model';
import { IUniversity } from 'app/shared/model/university.model';
import { IEducType } from 'app/shared/model/educ-type.model';

export interface IEducation {
  id?: number;
  email?: string;
  idUniver?: number;
  idEducType?: string;
  faculty?: string;
  specialty?: string;
  specialization?: string;
  startYear?: number;
  endYear?: number;
  email?: IEmployee;
  idUniver?: IUniversity;
  idEducType?: IEducType;
}

export const defaultValue: Readonly<IEducation> = {};

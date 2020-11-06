import { IEducation } from 'app/shared/model/education.model';

export interface IEducType {
  id?: number;
  idEducType?: number;
  educTypeNm?: string;
  idEducTypes?: IEducation[];
}

export const defaultValue: Readonly<IEducType> = {};

import { IEducation } from 'app/shared/model/education.model';

export interface IUniversity {
  id?: number;
  idUniver?: number;
  univerNm?: string;
  idUnivers?: IEducation[];
}

export const defaultValue: Readonly<IUniversity> = {};

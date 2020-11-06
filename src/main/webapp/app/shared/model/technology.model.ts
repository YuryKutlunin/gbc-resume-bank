import { IProjectTechnology } from 'app/shared/model/project-technology.model';

export interface ITechnology {
  id?: number;
  idTechnology?: number;
  technologyNm?: string;
  idTechnologies?: IProjectTechnology[];
}

export const defaultValue: Readonly<ITechnology> = {};

import { IProject } from 'app/shared/model/project.model';
import { ITechnology } from 'app/shared/model/technology.model';

export interface IProjectTechnology {
  id?: number;
  idProject?: number;
  idTechnology?: number;
  idProject?: IProject;
  idProject?: ITechnology;
}

export const defaultValue: Readonly<IProjectTechnology> = {};

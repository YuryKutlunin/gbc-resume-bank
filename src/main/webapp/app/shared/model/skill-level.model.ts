import { IEmployeeSkill } from 'app/shared/model/employee-skill.model';

export interface ISkillLevel {
  id?: number;
  idLevel?: number;
  levelDesc?: string;
  idLevels?: IEmployeeSkill[];
}

export const defaultValue: Readonly<ISkillLevel> = {};

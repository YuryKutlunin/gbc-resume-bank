import { IEmployee } from 'app/shared/model/employee.model';
import { ISkill } from 'app/shared/model/skill.model';
import { ISkillLevel } from 'app/shared/model/skill-level.model';

export interface IEmployeeSkill {
  id?: number;
  email?: string;
  idSkill?: number;
  idLevel?: number;
  email?: IEmployee;
  idSkill?: ISkill;
  idLevel?: ISkillLevel;
}

export const defaultValue: Readonly<IEmployeeSkill> = {};

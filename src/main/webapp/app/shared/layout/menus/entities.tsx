import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Translate, translate } from 'react-jhipster';
import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown
    icon="th-list"
    name={translate('global.menu.entities.main')}
    id="entity-menu"
    style={{ maxHeight: '80vh', overflow: 'auto' }}
  >
    <MenuItem icon="asterisk" to="/region">
      <Translate contentKey="global.menu.entities.region" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/country">
      <Translate contentKey="global.menu.entities.country" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/location">
      <Translate contentKey="global.menu.entities.location" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/department">
      <Translate contentKey="global.menu.entities.department" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/task">
      <Translate contentKey="global.menu.entities.task" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/employee">
      <Translate contentKey="global.menu.entities.employee" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/job">
      <Translate contentKey="global.menu.entities.job" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/job-history">
      <Translate contentKey="global.menu.entities.jobHistory" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/resource-pool">
      <Translate contentKey="global.menu.entities.resourcePool" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/job-title">
      <Translate contentKey="global.menu.entities.jobTitle" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/education">
      <Translate contentKey="global.menu.entities.education" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/university">
      <Translate contentKey="global.menu.entities.university" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/educ-type">
      <Translate contentKey="global.menu.entities.educType" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/project">
      <Translate contentKey="global.menu.entities.project" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/project-technology">
      <Translate contentKey="global.menu.entities.projectTechnology" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/technology">
      <Translate contentKey="global.menu.entities.technology" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/employee-project">
      <Translate contentKey="global.menu.entities.employeeProject" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/project-role">
      <Translate contentKey="global.menu.entities.projectRole" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/certificate">
      <Translate contentKey="global.menu.entities.certificate" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/employee-certif">
      <Translate contentKey="global.menu.entities.employeeCertif" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/skill">
      <Translate contentKey="global.menu.entities.skill" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/employee-skill">
      <Translate contentKey="global.menu.entities.employeeSkill" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/skill-level">
      <Translate contentKey="global.menu.entities.skillLevel" />
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);

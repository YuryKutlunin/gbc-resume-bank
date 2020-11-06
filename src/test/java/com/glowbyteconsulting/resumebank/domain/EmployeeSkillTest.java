package com.glowbyteconsulting.resumebank.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.glowbyteconsulting.resumebank.web.rest.TestUtil;

public class EmployeeSkillTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeeSkill.class);
        EmployeeSkill employeeSkill1 = new EmployeeSkill();
        employeeSkill1.setId(1L);
        EmployeeSkill employeeSkill2 = new EmployeeSkill();
        employeeSkill2.setId(employeeSkill1.getId());
        assertThat(employeeSkill1).isEqualTo(employeeSkill2);
        employeeSkill2.setId(2L);
        assertThat(employeeSkill1).isNotEqualTo(employeeSkill2);
        employeeSkill1.setId(null);
        assertThat(employeeSkill1).isNotEqualTo(employeeSkill2);
    }
}

package com.glowbyteconsulting.resumebank.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.glowbyteconsulting.resumebank.web.rest.TestUtil;

public class EmployeeProjectTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeeProject.class);
        EmployeeProject employeeProject1 = new EmployeeProject();
        employeeProject1.setId(1L);
        EmployeeProject employeeProject2 = new EmployeeProject();
        employeeProject2.setId(employeeProject1.getId());
        assertThat(employeeProject1).isEqualTo(employeeProject2);
        employeeProject2.setId(2L);
        assertThat(employeeProject1).isNotEqualTo(employeeProject2);
        employeeProject1.setId(null);
        assertThat(employeeProject1).isNotEqualTo(employeeProject2);
    }
}

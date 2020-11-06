package com.glowbyteconsulting.resumebank.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.glowbyteconsulting.resumebank.web.rest.TestUtil;

public class EmployeeCertifTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeeCertif.class);
        EmployeeCertif employeeCertif1 = new EmployeeCertif();
        employeeCertif1.setId(1L);
        EmployeeCertif employeeCertif2 = new EmployeeCertif();
        employeeCertif2.setId(employeeCertif1.getId());
        assertThat(employeeCertif1).isEqualTo(employeeCertif2);
        employeeCertif2.setId(2L);
        assertThat(employeeCertif1).isNotEqualTo(employeeCertif2);
        employeeCertif1.setId(null);
        assertThat(employeeCertif1).isNotEqualTo(employeeCertif2);
    }
}

package com.glowbyteconsulting.resumebank.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.glowbyteconsulting.resumebank.web.rest.TestUtil;

public class CertificateTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Certificate.class);
        Certificate certificate1 = new Certificate();
        certificate1.setId(1L);
        Certificate certificate2 = new Certificate();
        certificate2.setId(certificate1.getId());
        assertThat(certificate1).isEqualTo(certificate2);
        certificate2.setId(2L);
        assertThat(certificate1).isNotEqualTo(certificate2);
        certificate1.setId(null);
        assertThat(certificate1).isNotEqualTo(certificate2);
    }
}

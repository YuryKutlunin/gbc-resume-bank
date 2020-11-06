package com.glowbyteconsulting.resumebank.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.glowbyteconsulting.resumebank.web.rest.TestUtil;

public class EducTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EducType.class);
        EducType educType1 = new EducType();
        educType1.setId(1L);
        EducType educType2 = new EducType();
        educType2.setId(educType1.getId());
        assertThat(educType1).isEqualTo(educType2);
        educType2.setId(2L);
        assertThat(educType1).isNotEqualTo(educType2);
        educType1.setId(null);
        assertThat(educType1).isNotEqualTo(educType2);
    }
}

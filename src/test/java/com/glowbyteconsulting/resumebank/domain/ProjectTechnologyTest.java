package com.glowbyteconsulting.resumebank.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.glowbyteconsulting.resumebank.web.rest.TestUtil;

public class ProjectTechnologyTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProjectTechnology.class);
        ProjectTechnology projectTechnology1 = new ProjectTechnology();
        projectTechnology1.setId(1L);
        ProjectTechnology projectTechnology2 = new ProjectTechnology();
        projectTechnology2.setId(projectTechnology1.getId());
        assertThat(projectTechnology1).isEqualTo(projectTechnology2);
        projectTechnology2.setId(2L);
        assertThat(projectTechnology1).isNotEqualTo(projectTechnology2);
        projectTechnology1.setId(null);
        assertThat(projectTechnology1).isNotEqualTo(projectTechnology2);
    }
}

package com.glowbyteconsulting.resumebank.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.glowbyteconsulting.resumebank.web.rest.TestUtil;

public class ProjectRoleTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProjectRole.class);
        ProjectRole projectRole1 = new ProjectRole();
        projectRole1.setId(1L);
        ProjectRole projectRole2 = new ProjectRole();
        projectRole2.setId(projectRole1.getId());
        assertThat(projectRole1).isEqualTo(projectRole2);
        projectRole2.setId(2L);
        assertThat(projectRole1).isNotEqualTo(projectRole2);
        projectRole1.setId(null);
        assertThat(projectRole1).isNotEqualTo(projectRole2);
    }
}

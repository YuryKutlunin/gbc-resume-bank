package com.glowbyteconsulting.resumebank.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.glowbyteconsulting.resumebank.web.rest.TestUtil;

public class ResourcePoolTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResourcePool.class);
        ResourcePool resourcePool1 = new ResourcePool();
        resourcePool1.setId(1L);
        ResourcePool resourcePool2 = new ResourcePool();
        resourcePool2.setId(resourcePool1.getId());
        assertThat(resourcePool1).isEqualTo(resourcePool2);
        resourcePool2.setId(2L);
        assertThat(resourcePool1).isNotEqualTo(resourcePool2);
        resourcePool1.setId(null);
        assertThat(resourcePool1).isNotEqualTo(resourcePool2);
    }
}

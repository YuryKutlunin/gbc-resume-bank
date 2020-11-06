package com.glowbyteconsulting.resumebank.config;

import io.github.jhipster.config.JHipsterProperties;
import io.github.jhipster.config.cache.PrefixedKeyGenerator;
import java.time.Duration;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {
    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration =
            Eh107Configuration.fromEhcacheCacheConfiguration(
                CacheConfigurationBuilder
                    .newCacheConfigurationBuilder(Object.class, Object.class, ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                    .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                    .build()
            );
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.glowbyteconsulting.resumebank.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.glowbyteconsulting.resumebank.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.glowbyteconsulting.resumebank.domain.User.class.getName());
            createCache(cm, com.glowbyteconsulting.resumebank.domain.Authority.class.getName());
            createCache(cm, com.glowbyteconsulting.resumebank.domain.User.class.getName() + ".authorities");
            createCache(cm, com.glowbyteconsulting.resumebank.domain.Region.class.getName());
            createCache(cm, com.glowbyteconsulting.resumebank.domain.Country.class.getName());
            createCache(cm, com.glowbyteconsulting.resumebank.domain.Location.class.getName());
            createCache(cm, com.glowbyteconsulting.resumebank.domain.Department.class.getName());
            createCache(cm, com.glowbyteconsulting.resumebank.domain.Department.class.getName() + ".employees");
            createCache(cm, com.glowbyteconsulting.resumebank.domain.Task.class.getName());
            createCache(cm, com.glowbyteconsulting.resumebank.domain.Task.class.getName() + ".jobs");
            createCache(cm, com.glowbyteconsulting.resumebank.domain.Employee.class.getName());
            createCache(cm, com.glowbyteconsulting.resumebank.domain.Employee.class.getName() + ".jobs");
            createCache(cm, com.glowbyteconsulting.resumebank.domain.Job.class.getName());
            createCache(cm, com.glowbyteconsulting.resumebank.domain.Job.class.getName() + ".tasks");
            createCache(cm, com.glowbyteconsulting.resumebank.domain.JobHistory.class.getName());
            createCache(cm, com.glowbyteconsulting.resumebank.domain.Employee.class.getName() + ".emailCurators");
            createCache(cm, com.glowbyteconsulting.resumebank.domain.Employee.class.getName() + ".emails");
            createCache(cm, com.glowbyteconsulting.resumebank.domain.ResourcePool.class.getName());
            createCache(cm, com.glowbyteconsulting.resumebank.domain.ResourcePool.class.getName() + ".idResourcePools");
            createCache(cm, com.glowbyteconsulting.resumebank.domain.JobTitle.class.getName());
            createCache(cm, com.glowbyteconsulting.resumebank.domain.JobTitle.class.getName() + ".idTitles");
            createCache(cm, com.glowbyteconsulting.resumebank.domain.Education.class.getName());
            createCache(cm, com.glowbyteconsulting.resumebank.domain.University.class.getName());
            createCache(cm, com.glowbyteconsulting.resumebank.domain.University.class.getName() + ".idUnivers");
            createCache(cm, com.glowbyteconsulting.resumebank.domain.EducType.class.getName());
            createCache(cm, com.glowbyteconsulting.resumebank.domain.EducType.class.getName() + ".idEducTypes");
            createCache(cm, com.glowbyteconsulting.resumebank.domain.Project.class.getName());
            createCache(cm, com.glowbyteconsulting.resumebank.domain.Project.class.getName() + ".idProjects");
            createCache(cm, com.glowbyteconsulting.resumebank.domain.ProjectTechnology.class.getName());
            createCache(cm, com.glowbyteconsulting.resumebank.domain.Technology.class.getName());
            createCache(cm, com.glowbyteconsulting.resumebank.domain.Technology.class.getName() + ".idTechnologies");
            createCache(cm, com.glowbyteconsulting.resumebank.domain.EmployeeProject.class.getName());
            createCache(cm, com.glowbyteconsulting.resumebank.domain.ProjectRole.class.getName());
            createCache(cm, com.glowbyteconsulting.resumebank.domain.ProjectRole.class.getName() + ".idRoles");
            createCache(cm, com.glowbyteconsulting.resumebank.domain.Certificate.class.getName());
            createCache(cm, com.glowbyteconsulting.resumebank.domain.Certificate.class.getName() + ".idCertificates");
            createCache(cm, com.glowbyteconsulting.resumebank.domain.EmployeeCertif.class.getName());
            createCache(cm, com.glowbyteconsulting.resumebank.domain.Skill.class.getName());
            createCache(cm, com.glowbyteconsulting.resumebank.domain.Skill.class.getName() + ".idSkills");
            createCache(cm, com.glowbyteconsulting.resumebank.domain.EmployeeSkill.class.getName());
            createCache(cm, com.glowbyteconsulting.resumebank.domain.SkillLevel.class.getName());
            createCache(cm, com.glowbyteconsulting.resumebank.domain.SkillLevel.class.getName() + ".idLevels");
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache == null) {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}

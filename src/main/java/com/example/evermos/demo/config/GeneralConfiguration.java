package com.example.evermos.demo.config;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.boot.actuate.autoconfigure.web.server.ManagementContextAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GeneralConfiguration {

    public static BeanFactoryPostProcessor ensureEagerManagementServerInitializationPostProcessor() {
        return beanFactory -> {
            String managementPrefix = ManagementContextAutoConfiguration.class.getName() + "$";
            for (String definitionName : beanFactory.getBeanDefinitionNames()) {
                if (definitionName.startsWith(managementPrefix)) {
                    beanFactory.getBeanDefinition(definitionName).setLazyInit(false);
                }
            }
        };
    }
    @Bean
    public MeterRegistryCustomizer<MeterRegistry> metricsCommonTags() {
        return registry -> registry.config().commonTags("application","demo");
    }
}

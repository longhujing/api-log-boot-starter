package com.nanfeng.api.log.config;

import com.nanfeng.api.log.components.WebLogInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author nanfeng
 */
@Configuration
@ConditionalOnClass(WebLogInterceptor.class)
@EnableConfigurationProperties(BaseLogProperties.class)
public class LogInterceptorAutoConfiguration {

    @Autowired
    private BaseLogProperties baseLogProperties;

    @Bean
    @ConditionalOnMissingBean(WebLogInterceptor.class)
    @ConditionalOnProperty(prefix = "api.log", value = "enabled", matchIfMissing = true)
    public WebLogInterceptor logInterceptor() {
        return new WebLogInterceptor();
    }

}

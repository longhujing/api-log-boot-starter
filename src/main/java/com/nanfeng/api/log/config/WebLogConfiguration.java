package com.nanfeng.api.log.config;

import com.nanfeng.api.log.components.WebLogInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author nanfeng
 */
@Configuration
@Slf4j
public class WebLogConfiguration extends WebMvcConfigurationSupport {

    private final WebLogInterceptor webLogInterceptor;
    private final BaseLogProperties logProperties;

    public WebLogConfiguration(WebLogInterceptor webLogInterceptor,
                               BaseLogProperties logProperties) {
        this.webLogInterceptor = webLogInterceptor;
        this.logProperties = logProperties;
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        if (logProperties.isEnabled()) {
            if (CollectionUtils.isEmpty(logProperties.getPaths())) {
                log.info("[WebLogConfiguration] api.log.paths 匹配路径为空");
                return;
            }

            registry.addInterceptor(webLogInterceptor).addPathPatterns(logProperties.getPaths());
        }

    }
}

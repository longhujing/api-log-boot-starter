package com.nanfeng.api.log.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 日志配置
 *
 * @author nanfeng
 */
@ConfigurationProperties(prefix = "api.log")
@Component
@Data
public class BaseLogProperties {

    private List<String> paths;

    private boolean enabled;

}

package com.hbhb.cw.authserver.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import lombok.Data;

/**
 * 免认证url资源（白名单）
 * @author xiaokang
 * @since 2020-10-10
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "whitelist")
public class WhiteListConfig {
    private List<String> urls;
}

package com.hbhb.cw.authserver.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * @author xiaokang
 * @since 2020-06-23
 */
@Component
public class KaptchaConfig {
    @Bean
    public DefaultKaptcha getDefaultKaptcha() {
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        Properties properties = new Properties();
        // 图片高宽
        properties.setProperty("kaptcha.image.width", "120");
        properties.setProperty("kaptcha.image.height", "40");
        // 图片边框
        properties.setProperty("kaptcha.border", "no");
        // 字体类型
        properties.setProperty("kaptcha.textproducer.font.names", "Arial");
        // 字体大小
        properties.setProperty("kaptcha.textproducer.font.size", "30");
        // 验证码设置
        properties.setProperty("kaptcha.textproducer.char.length", "4");
        properties.setProperty("kaptcha.textproducer.char.space", "5");
        properties.setProperty("kaptcha.textproducer.char.string", "123456789ABCDEFGHJKLMNPQRSTUVWXYZ");
        // 燥点
        properties.setProperty("kaptcha.noise.color", "35,37,38");
        Config config = new Config(properties);
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }
}

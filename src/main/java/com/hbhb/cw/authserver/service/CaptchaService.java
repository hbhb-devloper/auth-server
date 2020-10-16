package com.hbhb.cw.authserver.service;

import com.google.code.kaptcha.impl.DefaultKaptcha;

import com.hbhb.core.constants.AuthConstant;
import com.hbhb.cw.authserver.bean.Captcha;
import com.hbhb.cw.authserver.enums.CaptchaErrorCode;
import com.hbhb.cw.authserver.exception.CaptchaException;
import com.hbhb.redis.component.RedisHelper;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.imageio.ImageIO;

import lombok.extern.slf4j.Slf4j;

/**
 * @author xiaokang
 * @since 2020-10-11
 */
@Service
@Slf4j
public class CaptchaService {

    @Resource
    private DefaultKaptcha defaultKaptcha;
    @Resource
    private RedisHelper redisHelper;

    /**
     * 生成验证码
     */
    public Captcha getCaptcha() {
        Captcha captcha = new Captcha();
        // 生成key
        String captchaKey = UUID.randomUUID().toString().replaceAll("-", "");
        String verifyKey = AuthConstant.CAPTCHA_CODE_KEY.value() + captchaKey;
        captcha.setKey(captchaKey);

        // 生成随机字串
        String verifyCode = defaultKaptcha.createText();
        redisHelper.set(verifyKey, verifyCode,
                Long.parseLong(AuthConstant.CAPTCHA_EXPIRATION.value()), TimeUnit.MINUTES);

        // 生成图片
        try (ByteArrayOutputStream stream = new ByteArrayOutputStream()) {
            BufferedImage bi = defaultKaptcha.createImage(verifyCode);
            ImageIO.write(bi, "jpg", stream);
            String img = Base64.getEncoder().encodeToString(stream.toByteArray());
            captcha.setImg(img);
        } catch (Exception e) {
            throw new CaptchaException(CaptchaErrorCode.CAPTCHA_GENERATE_ERROR);

        }
        return captcha;
    }

    /**
     * 校验验证码
     */
    public Boolean checkCaptcha(String captcha, String captchaKey) {
        // 验证码校验
        if (captcha == null) {
            throw new CaptchaException(CaptchaErrorCode.CAPTCHA_MISSING);
        }
        String verifyKey = AuthConstant.CAPTCHA_CODE_KEY.value() + captchaKey;
        String captchaRedis = redisHelper.get(verifyKey);
        if (StringUtils.isEmpty(captchaRedis)) {
            throw new CaptchaException(CaptchaErrorCode.CAPTCHA_EXPIRED);
        }
        if (!captcha.equalsIgnoreCase(captchaRedis)) {
            throw new CaptchaException(CaptchaErrorCode.CAPTCHA_MISMATCH);
        }
        return true;
    }
}

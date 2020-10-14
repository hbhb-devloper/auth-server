package com.hbhb.cw.authserver.web.controller;

import com.hbhb.cw.authserver.bean.Captcha;
import com.hbhb.cw.authserver.service.CaptchaService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * @author xiaokang
 * @since 2020-10-12
 */
@Tag(name = "验证码")
@RestController
@RequestMapping("/captcha")
public class CaptchaController {

    @Resource
    private CaptchaService captchaService;

    @Operation(summary = "获取验证码")
    @GetMapping("")
    public Captcha getCaptcha() {
        return captchaService.getCaptcha();
    }

    @Operation(summary = "校验验证码")
    @GetMapping("/check")
    public Boolean checkCaptcha(@Parameter(description = "验证码", required = true) @RequestParam String captcha,
                                @Parameter(description = "验证码Key", required = true) @RequestParam String captchaKey) {
        return captchaService.checkCaptcha(captcha, captchaKey);
    }
}

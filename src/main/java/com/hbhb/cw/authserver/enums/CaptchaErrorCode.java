package com.hbhb.cw.authserver.enums;

import lombok.Getter;

/**
 * @author xiaokang
 * @since 2020-10-06
 */
@Getter
public enum CaptchaErrorCode {

    CAPTCHA_GENERATE_ERROR("A0100", "captcha.generate.error"),
    CAPTCHA_MISSING("A0101", "captcha.missing"),
    CAPTCHA_EXPIRED("A0102", "captcha.expired"),
    CAPTCHA_MISMATCH("A0103", "captcha.mismatch"),
    ;

    private final String code;
    private final String message;

    CaptchaErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
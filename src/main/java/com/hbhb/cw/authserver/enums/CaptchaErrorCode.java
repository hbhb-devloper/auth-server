package com.hbhb.cw.authserver.enums;

import lombok.Getter;

/**
 * @author xiaokang
 * @since 2020-10-06
 */
@Getter
public enum CaptchaErrorCode {

    CAPTCHA_GENERATE_ERROR("90006", "captcha.generate.error"),
    CAPTCHA_MISSING("90007", "captcha.missing"),
    CAPTCHA_EXPIRED("90008", "captcha.expired"),
    CAPTCHA_MISMATCH("90009", "captcha.mismatch"),
    ;

    private final String code;

    private final String message;

    CaptchaErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}

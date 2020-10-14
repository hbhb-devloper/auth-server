package com.hbhb.cw.authserver.enums;

import lombok.Getter;

/**
 * @author xiaokang
 * @since 2020-10-06
 */
@Getter
public enum AuthErrorCode {

    USER_NOT_FOUND("A0200","用户不存在!"),
    USERNAME_OR_PASSWORD_ERROR("A0201","用户名或密码错误!"),
    USER_DISABLED("A0202","该账户已被禁用!"),
    USER_LOCKED("A0203","该账号已被锁定!"),
    USER_ACCOUNT_EXPIRED("A0204","该账号已过期!"),
    USER_CREDENTIALS_EXPIRED("A0205","该账户的登录凭证已过期，请重新登录!"),

    ;

    private String code;

    private String message;

    AuthErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}

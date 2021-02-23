package com.hbhb.cw.authserver.enums;

import lombok.Getter;

/**
 * @author xiaokang
 * @since 2020-10-06
 */
@Getter
public enum AuthErrorCode {

    USER_NOT_FOUND("A0200", "user.not.found"),
    USERNAME_OR_PASSWORD_ERROR("A0201", "username.or.password.error"),
    USER_DISABLED("A0202", "user.disabled"),
    USER_LOCKED("A0203", "user.locked"),
    USER_ACCOUNT_EXPIRED("A0204", "user.account.expired"),
    USER_CREDENTIALS_EXPIRED("A0205", "user.credentials.expired"),
    EMAIL_INPUT_ERROR("A0206", "email.input.error"),
    CONNECTION_TIMEOUT("A0207","connection.timeout"),
    ;


    private final String code;
    private final String message;

    AuthErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
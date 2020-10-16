package com.hbhb.cw.authserver.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hbhb.cw.authserver.component.AuthExceptionSerializer;

import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

import lombok.Getter;

/**
 * @author xiaokang
 * @since 2020-10-09
 */
@JsonSerialize(using = AuthExceptionSerializer.class)
public class AuthException extends OAuth2Exception {
    private static final long serialVersionUID = -6727383784584365441L;

    @Getter
    private final String oAuth2ErrorCode;

    public AuthException(String msg, String oAuth2ErrorCode) {
        super(msg);
        this.oAuth2ErrorCode = oAuth2ErrorCode;
    }
}

package com.hbhb.cw.authserver.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hbhb.core.bean.MessageConvert;

import com.hbhb.cw.authserver.enums.AuthErrorCode;
import com.hbhb.web.exception.BusinessException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

import lombok.Getter;

/**
 * @author xiaokang
 * @since 2020-10-09
 */
//@JsonSerialize(using = AuthExceptionSerializer.class)
//public class AuthException extends OAuth2Exception {
@Getter
public class AuthException extends BusinessException {
    private static final long serialVersionUID = -6727383784584365441L;

//    @Getter
//    private final String oAuth2ErrorCode;

    private final String code;

    public AuthException(AuthErrorCode errorCode) {
        super(errorCode.getCode(), MessageConvert.convert(errorCode.getMessage()));
        this.code = errorCode.getCode();
    }
}

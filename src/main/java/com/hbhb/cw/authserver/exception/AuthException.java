package com.hbhb.cw.authserver.exception;

import com.hbhb.core.bean.MessageConvert;
import com.hbhb.cw.authserver.enums.AuthErrorCode;
import com.hbhb.web.exception.BusinessException;

import lombok.Getter;

/**
 * @author xiaokang
 * @since 2020-10-09
 */
@Getter
public class AuthException extends BusinessException {
    private static final long serialVersionUID = -6727383784584365441L;

    private final String code;

    public AuthException(AuthErrorCode errorCode) {
        super(errorCode.getCode(), MessageConvert.convert(errorCode.getMessage()));
        this.code = errorCode.getCode();
    }
}

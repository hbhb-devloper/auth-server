package com.hbhb.cw.authserver.exception;

import com.hbhb.core.bean.MessageConvert;
import com.hbhb.cw.authserver.enums.CaptchaErrorCode;
import com.hbhb.web.exception.BusinessException;

import lombok.Getter;

/**
 * @author xiaokang
 * @since 2020-10-06
 */
@Getter
public class CaptchaException extends BusinessException {
    private static final long serialVersionUID = -9083303123097052232L;

    private final String code;

    public CaptchaException(CaptchaErrorCode errorCode) {
        super(errorCode.getCode(), MessageConvert.convert(errorCode.getMessage()));
        this.code = errorCode.getCode();
    }
}

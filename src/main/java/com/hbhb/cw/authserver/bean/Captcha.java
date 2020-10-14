package com.hbhb.cw.authserver.bean;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author xiaokang
 * @since 2020-06-22
 */
@Data
public class Captcha {
    @Schema(description = "验证码key")
    private String key;
    @Schema(description = "验证码图案")
    private String img;
}

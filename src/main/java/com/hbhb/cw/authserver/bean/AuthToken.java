package com.hbhb.cw.authserver.bean;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xiaokang
 * @since 2020-10-10
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthToken implements Serializable {
    private static final long serialVersionUID = 7320258002662237907L;

    @Schema(description = "访问令牌")
    private String access_token;
    @Schema(description = "刷新令牌")
    private String refresh_token;
    @Schema(description = "有效时间(秒)")
    private int expires_in;
}

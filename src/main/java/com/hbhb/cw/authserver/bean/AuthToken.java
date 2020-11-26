package com.hbhb.cw.authserver.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

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
    @JsonProperty("access_token")
    private String accessToken;

    @Schema(description = "刷新令牌")
    @JsonProperty("refresh_token")
    private String refreshToken;

    @Schema(description = "有效时间(秒)")
    @JsonProperty("expires_in")
    private int expiresIn;
}

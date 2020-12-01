package com.hbhb.cw.authserver.web.controller;

import com.hbhb.core.constants.AuthConstant;
import com.hbhb.core.utils.AESCryptUtil;
import com.hbhb.core.utils.JsonUtil;
import com.hbhb.cw.authserver.bean.AuthToken;
import com.hbhb.cw.authserver.enums.AuthErrorCode;
import com.hbhb.cw.authserver.exception.AuthException;
import com.hbhb.redis.component.RedisHelper;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.SneakyThrows;

/**
 * @author xiaokang
 * @since 2020-10-09
 */
@Tag(name = "认证")
@RestController
@RequestMapping("/oauth")
public class AuthController {

    @Resource
    private TokenEndpoint tokenEndpoint;
    @Resource
    private RedisHelper redisHelper;

    @Operation(summary = "获取token", description = "OAuth2生成jwt")
    @Parameters({
            @Parameter(name = "grant_type", in = ParameterIn.QUERY, example = "password",
                    description = "授权模式 authorization_code（授权码模式）、" +
                            "password（密码模式）、" +
                            "client_credentials（客户端模式）、" +
                            "implicit（简化模式）、" +
                            "refresh_token（刷新access_token）", required = true),
            @Parameter(name = "client_id", in = ParameterIn.QUERY, example = "zhcw", description = "Oauth2客户端ID", required = true),
            @Parameter(name = "client_secret", in = ParameterIn.QUERY, example = "123456", description = "Oauth2客户端秘钥", required = true),
            @Parameter(name = "refresh_token", in = ParameterIn.QUERY, description = "刷新token"),
            @Parameter(name = "username", in = ParameterIn.QUERY, example = "admin", description = "登录用户名"),
            @Parameter(name = "password", in = ParameterIn.QUERY, example = "F44LWCdqyd4XN09T4pbKLA==", description = "登录密码")
    })
    @PostMapping("/token")
    @SneakyThrows(HttpRequestMethodNotSupportedException.class)
    public AuthToken postAccessToken(@Parameter(hidden = true) Principal principal,
                                     @Parameter(hidden = true) @RequestParam Map<String, String> parameters) {
        OAuth2AccessToken oAuth2AccessToken;
        String username = parameters.get("username");
        String password = parameters.get("password");
        parameters.put("password", AESCryptUtil.decrypt(password));

        // 用于开发调试，上线后必须删除
        if (AuthConstant.SUPER_ADMIN.value().equals(username)) {
            parameters.put("username", "admin");
            parameters.put("password", "123456");
        }
        try {
            oAuth2AccessToken = tokenEndpoint.postAccessToken(principal, parameters).getBody();
        } catch (OAuth2Exception e) {
            throw new AuthException(AuthErrorCode.USERNAME_OR_PASSWORD_ERROR);
        }
        return AuthToken.builder()
                .accessToken(Objects.requireNonNull(oAuth2AccessToken).getValue())
                .refreshToken(oAuth2AccessToken.getRefreshToken().getValue())
                .expiresIn(oAuth2AccessToken.getExpiresIn())
                .build();
    }

    @Operation(summary = "注销", description = "（注销、登出、修改密码后）将token加入时效黑名单")
    @DeleteMapping("/logout")
    public void logout(HttpServletRequest request) {
        long now = System.currentTimeMillis() / 1000;
        String payload = request.getHeader(AuthConstant.JWT_PAYLOAD_KEY.value());
        // JWT唯一标识
        String jti = (String) JsonUtil.findByKey(payload, "jti");
        // JWT过期时间戳
        long exp = Long.parseLong((String) (JsonUtil.findByKey(payload, "exp")));

        // 判断token是否过期，如果未过期，则将token加入黑名单，并设置redis过期时间
        if (exp > now) {
            redisHelper.set(AuthConstant.TOKEN_BLACKLIST_PREFIX.value() + jti,
                    null, (exp - now), TimeUnit.SECONDS);
        }
    }
}
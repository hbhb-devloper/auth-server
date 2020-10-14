package com.hbhb.cw.authserver.enums;

/**
 * @author xiaokang
 * @since 2020-10-07
 */
public enum GrantTypeEnum {

    /**
     * 授权码模式
     */
    AUTHORIZATION_CODE("authorization_code"),
    /**
     * 密码模式
     */
    PASSWORD("password"),
    /**
     * 客户端模式
     */
    CLIENT_CREDENTIALS("client_credentials"),
    /**
     * 简化模式
     */
    IMPLICIT("implicit"),
    /**
     * 刷新access_token
     */
    REFRESH_TOKEN("refresh_token"),
    ;

    private final String value;

    GrantTypeEnum(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }
}

package com.hbhb.cw.authserver.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hbhb.cw.authserver.enums.AuthEnum;
import com.hbhb.cw.systemcenter.model.SysUser;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登录用户身份权限
 *
 * @author xiaokang
 */
@Data
@NoArgsConstructor
public class LoginUser implements UserDetails {
    private static final long serialVersionUID = 4854326102327366290L;

    private Integer id;
    @Schema(description = "OAuth2客户端id")
    private String clientId;
    @Schema(description = "登录用户名")
    private String username;
    @Schema(description = "登录密码")
    private String password;
    private Boolean enabled;
    @Schema(description = "权限集合")
    private Collection<SimpleGrantedAuthority> authorities;

    public LoginUser(SysUser user, String clientId, Collection<SimpleGrantedAuthority> authorities) {
        this.setId(user.getId());
        this.setUsername(user.getUserName());
        this.setPassword(AuthEnum.BCRYPT.value() + user.getPwd());
        this.setEnabled(user.getState() == 1);
        this.setClientId(clientId);
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return this.password;
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return this.username;
    }

    /**
     * 账户是否未过期（过期无法验证）
     */
    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 账户是否解锁（锁定的用户无法进行身份验证）
     */
    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 账户凭证（即密码）是否已过期（过期的凭据不可认证）
     */
    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 账户是否可用（禁用的用户不能身份验证）
     */
    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}

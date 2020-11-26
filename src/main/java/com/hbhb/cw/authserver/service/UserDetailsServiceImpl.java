package com.hbhb.cw.authserver.service;

import com.hbhb.core.constants.AuthConstant;
import com.hbhb.cw.authserver.bean.LoginUser;
import com.hbhb.cw.authserver.enums.AuthErrorCode;
import com.hbhb.cw.authserver.rpc.UserApiExp;
import com.hbhb.cw.systemcenter.model.User;

import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

/**
 * 用户验证处理
 *
 * @author xiaokang
 */
@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private UserApiExp userApi;
    @Resource
    private HttpServletRequest request;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String clientId = request.getParameter(AuthConstant.JWT_CLIENT_ID_KEY.value());
        User user = userApi.getUserByName(username);
        if (StringUtils.isEmpty(user)) {
            throw new UsernameNotFoundException(AuthErrorCode.USER_NOT_FOUND.getMessage());
        }

        // 获取登录用户的所有权限
        List<Integer> roleIds = userApi.getUserRoles(user.getId());
        Collection<SimpleGrantedAuthority> authorities = roleIds.stream()
                .map(roleId -> new SimpleGrantedAuthority(roleId.toString()))
                .collect(Collectors.toList());

        LoginUser loginUser = new LoginUser(user, clientId, authorities);
        if (!loginUser.isEnabled()) {
            throw new DisabledException(AuthErrorCode.USER_DISABLED.getMessage());
        } else if (!loginUser.isAccountNonLocked()) {
            throw new LockedException(AuthErrorCode.USER_LOCKED.getMessage());
        } else if (!loginUser.isAccountNonExpired()) {
            throw new AccountExpiredException(AuthErrorCode.USER_ACCOUNT_EXPIRED.getMessage());
        } else if (!loginUser.isCredentialsNonExpired()) {
            throw new CredentialsExpiredException(AuthErrorCode.USER_CREDENTIALS_EXPIRED.getMessage());
        }
        return loginUser;
    }
}

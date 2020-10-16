package com.hbhb.cw.authserver.component;

import com.hbhb.cw.authserver.enums.AuthErrorCode;
import com.hbhb.cw.authserver.exception.AuthException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.DefaultThrowableAnalyzer;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.ClientAuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InsufficientScopeException;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.web.util.ThrowableAnalyzer;
import org.springframework.stereotype.Component;

import lombok.SneakyThrows;

/**
 * 认证接口异常转换器
 * @author xiaokang
 * @since 2020-10-09
 */
@Component
@SuppressWarnings(value = {"rawtypes"})
public class AuthWebResponseExceptionTranslator implements WebResponseExceptionTranslator {

    private final ThrowableAnalyzer throwableAnalyzer = new DefaultThrowableAnalyzer();

    @Override
    @SneakyThrows
    public ResponseEntity<OAuth2Exception> translate(Exception e) {
        Throwable[] causeChain = throwableAnalyzer.determineCauseChain(e);
        Exception exception = (InvalidGrantException) throwableAnalyzer.getFirstThrowableOfType(
                InvalidGrantException.class, causeChain);
        if (exception != null) {
            return handleAuthException(new InvalidException());
        }
        return null;
    }


    private ResponseEntity<OAuth2Exception> handleAuthException(OAuth2Exception e) {
        int status = e.getHttpErrorCode();
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CACHE_CONTROL, "no-store");
        headers.set(HttpHeaders.PRAGMA, "no-cache");
        if (status == HttpStatus.UNAUTHORIZED.value() || (e instanceof InsufficientScopeException)) {
            headers.set(HttpHeaders.WWW_AUTHENTICATE,
                    String.format("%s %s", OAuth2AccessToken.BEARER_TYPE, e.getSummary()));
        }

        // 客户端异常直接返回客户端，不然无法解析
        if (e instanceof ClientAuthenticationException) {
            return new ResponseEntity<>(e, headers, HttpStatus.valueOf(status));
        }
        return new ResponseEntity<>(new AuthException(e.getMessage(), e.getOAuth2ErrorCode()), headers,
                HttpStatus.valueOf(status));
    }


    private static class InvalidException extends OAuth2Exception {
        private static final long serialVersionUID = -8873434436071464564L;

        public InvalidException() {
            super(AuthErrorCode.USERNAME_OR_PASSWORD_ERROR.getMessage());
        }

        @Override
        public String getOAuth2ErrorCode() {
            return AuthErrorCode.USERNAME_OR_PASSWORD_ERROR.getCode();
        }

        @Override
        public int getHttpErrorCode() {
            return HttpStatus.BAD_REQUEST.value();
        }
    }
}

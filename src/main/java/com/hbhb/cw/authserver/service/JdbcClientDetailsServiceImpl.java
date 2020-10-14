package com.hbhb.cw.authserver.service;

import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;

import javax.sql.DataSource;

import lombok.SneakyThrows;

/**
 * @author xiaokang
 * @since 2020-10-09
 */
public class JdbcClientDetailsServiceImpl extends JdbcClientDetailsService {

    public JdbcClientDetailsServiceImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    @SneakyThrows
    public ClientDetails loadClientByClientId(String clientId) {
        return super.loadClientByClientId(clientId);
    }
}

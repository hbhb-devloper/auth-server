package com.hbhb.cw.authserver.rpc;

import com.hbhb.cw.systemcenter.api.SysUserApi;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author xiaokang
 * @since 2020-09-18
 */
@FeignClient(value = "${provider.system-center}", url = "${feign-url}", path = "user")
public interface SysUserApiExp extends SysUserApi {
}
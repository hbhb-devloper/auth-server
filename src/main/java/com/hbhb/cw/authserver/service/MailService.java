package com.hbhb.cw.authserver.service;



import com.hbhb.cw.authserver.rpc.MailApiExp;
import com.hbhb.cw.messagehub.vo.MailVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author wangxiaogang
 */
@Service
@Slf4j
public class MailService {
    @Resource
    private MailApiExp mailApi;

    public void postMail(String receiver, String name, String title, String content) {
        mailApi.postMail(MailVO.builder()
                .receiver(receiver)
                .title(title)
                .content(String.format(content, name))
                .build());
    }
}

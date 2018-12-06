package com.vens.consumer.service;

import com.vens.activemq.model.MailModel;

/**
 * @author LuZhiqing
 * @Description:
 * @date 2018/12/6
 */
public interface MailService {
    void mailSend(MailModel mailModel);
}

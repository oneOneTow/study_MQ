package com.vens.consumer.mq;

import com.vens.activemq.model.MessageModel;

/**
 * @author LuZhiqing
 * @Description:
 * @date 2018/12/6
 */
public interface Consumer {
    MessageModel receiveMessage();
}

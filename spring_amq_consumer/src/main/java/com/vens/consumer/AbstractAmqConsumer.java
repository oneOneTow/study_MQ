package com.vens.consumer;

import com.vens.activemq.model.MessageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;

/**
 * @author LuZhiqing
 * @Description:
 * @date 2018/12/6
 */
public abstract class AbstractAmqConsumer implements Consumer{

    @Autowired
    private JmsTemplate jmsTemplate;
    public MessageModel receiveMessage(){
        return null;
    }

}

package com.vens.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;
import javax.jms.Message;

/**
 * @author LuZhiqing
 * @Description:
 * @date 2018/12/5
 */
public class AbstractAmpProducer implements Producer{
    @Autowired
    private JmsTemplate jmsTemplate;
    public Message produceMessage() {
        return null;
    }

    public Message sendMessage() {
        return null;
    }
}

package com.vens.producer;

import com.alibaba.fastjson.JSON;
import com.vens.activemq.model.MessageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 * @author LuZhiqing
 * @Description:
 * @date 2018/12/5
 */
public abstract class AbstractAmpProducer implements Producer{
    @Autowired
    private JmsTemplate jmsTemplate;
    public Message produceMessage() {
        return null;
    }

    public void sendMessage(final MessageModel msg) {
        jmsTemplate.send(new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(JSON.toJSONString(msg));
            }
        });
    }
}

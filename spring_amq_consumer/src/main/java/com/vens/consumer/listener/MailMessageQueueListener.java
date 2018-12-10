package com.vens.consumer.listener;

import com.alibaba.fastjson.JSON;
import com.vens.activemq.model.MailModel;
import com.vens.consumer.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.listener.SessionAwareMessageListener;
import org.springframework.stereotype.Component;

import javax.jms.*;

/**
 * @author LuZhiqing
 * @Description:
 * @date 2018/12/6
 */
@Component
public class MailMessageQueueListener implements SessionAwareMessageListener {
    @Autowired
    MailService mailServiceImpl;
    @Override
    public synchronized void onMessage(Message message, Session session) throws JMSException {
        TextMessage msg=(TextMessage)message;
        final String ms=msg.getText();
        System.out.println("receive message :"+ms);
        MailModel mailModel= JSON.parseObject(ms,MailModel.class);
        if(mailModel==null){
            return ;
        }
        mailServiceImpl.mailSend(mailModel);
        //ÊÖ¶¯Ó¦´ð
        msg.acknowledge();
    }
}

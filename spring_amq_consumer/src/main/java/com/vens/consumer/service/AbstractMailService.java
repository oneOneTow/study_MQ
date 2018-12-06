package com.vens.consumer.service;

import com.vens.activemq.model.MailModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * @author LuZhiqing
 * @Description:
 * @date 2018/12/6
 */
public abstract class AbstractMailService implements MailService{

    @Autowired
    ThreadPoolTaskExecutor threadPool;
    @Autowired
    JavaMailSenderImpl mailSender;
    public void mailSend(final MailModel mailModel){
        threadPool.execute(new Runnable() {
            public void run() {
                MimeMessage mimeMessage = mailSender.createMimeMessage();
                MimeMessageHelper messageHelper = null;
                try {
                    messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                    messageHelper.setFrom(mailModel.getFrom(), "ÏµÍ³Ãû³Æ");
                    messageHelper.setTo(mailModel.getTo());
                    messageHelper.setSubject(mailModel.getSubject());
                    messageHelper.setText(mailModel.getContent(), true);
                    mailSender.send(mimeMessage);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }
}

package com.vens.consumer.service;

import com.vens.activemq.model.MailModel;
import com.vens.consumer.helper.MailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

/**
 * @author LuZhiqing
 * @Description:
 * @date 2018/12/6
 */
@Service
public class MailServiceImpl implements MailService {

    @Autowired
    ThreadPoolTaskExecutor threadPool;
    @Autowired
    MailSender mailSender;

    @Override
    public void mailSend(final MailModel mailModel) {
        System.out.println("send email:"+mailModel);
        threadPool.execute(() -> {
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
        });
    }
}

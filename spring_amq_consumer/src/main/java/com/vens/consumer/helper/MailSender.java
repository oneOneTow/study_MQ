package com.vens.consumer.helper;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.lang.Nullable;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSenderImpl;


import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * @author LuZhiqing
 * @Description:
 * @date 2018/12/6
 */
public class MailSender extends JavaMailSenderImpl {
    private LinkedList<Transport> transportPool;
    private int poolSize = 4;

    @Override
    protected void doSend(MimeMessage[] mimeMessages, @Nullable Object[] originalMessages) throws MailException {
        Map<Object, Exception> failedMessages = new LinkedHashMap<>();
        Transport transport = null;
        try {
            transport = getTransport();
            for (int i = 0; i < mimeMessages.length; i++) {
                MimeMessage mimeMessage = mimeMessages[i];
                try {
                    if (mimeMessage.getSentDate() == null) {
                        mimeMessage.setSentDate(new Date());
                    }
                    String messageId = mimeMessage.getMessageID();
                    mimeMessage.saveChanges();
                    if (messageId != null) {
                        mimeMessage.setHeader("Message-ID", messageId);
                    }
                    transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
                } catch (Exception ex) {
                    Object original = (originalMessages != null ? originalMessages[i] : mimeMessage);
                    failedMessages.put(original, ex);
                }
            }
        } catch (MessagingException e) {
            throw new MailSendException("Failed to close server connection after message sending", e);
        } finally {
            try {
                if (null != transport) {
                    transport.close();
                }
            } catch (MessagingException e) {
                throw new MailSendException("Failed to close server connection after message sending", e);
            }
            if (!failedMessages.isEmpty()) {
                throw new MailSendException(failedMessages);
            }
        }
    }

    public synchronized Transport getTransport() throws MessagingException {
        if (transportPool == null) {
            transportPool=new LinkedList<>();
            for (int i = 0; i < this.poolSize; i++) {
                transportPool.add(connectTransport());
            }
            System.out.println("create transport pool");
        }
        if (transportPool.size() < 1) {
            System.out.println("wait ");
        }
        final Transport transport = transportPool.removeFirst();

        return (Transport)Enhancer.create(Transport.class, Transport.class.getInterfaces(), new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                if(!"close".equals(method.getName())){
                    return method.invoke(transport, objects);
                }
                transportPool.add(transport);
                System.out.println("¹Ø±ÕÁ¬½Ó³Ø");
                return null;
            }
        });
    }

}

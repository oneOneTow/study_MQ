package com.vens.consumer.helper;

import org.springframework.lang.Nullable;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author LuZhiqing
 * @Description:
 * @date 2018/12/6
 */
public class MailSender extends JavaMailSenderImpl {
    private Transport transport;

    public Transport getTransport() throws MessagingException {
        if (null == this.transport) {
            try {
                this.transport = connectTransport();
            } catch (AuthenticationFailedException ex) {
                throw new MailAuthenticationException(ex);
            }
        }
        return this.transport;
    }

    public void closeTransport() {
        try {
            if (this.transport != null) {
                this.transport.close();
            }
        } catch (Exception ex) {
            throw new MailSendException("Failed to close server connection after message sending", ex);
        }
    }

    public void setTransport(Transport transport) {
        this.transport = transport;
    }

    @Override
    protected void doSend(MimeMessage[] mimeMessages, @Nullable Object[] originalMessages) throws MailException {
        Map<Object, Exception> failedMessages = new LinkedHashMap<>();
        try {
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
        } finally {
            if (!failedMessages.isEmpty()) {
                throw new MailSendException(failedMessages);
            }
        }
    }
}

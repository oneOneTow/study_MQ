package com.vens.producer;


import com.vens.activemq.model.MessageModel;

import javax.jms.Message;

public interface Producer {
    Message produceMessage();
    void sendMessage(MessageModel msg);
}

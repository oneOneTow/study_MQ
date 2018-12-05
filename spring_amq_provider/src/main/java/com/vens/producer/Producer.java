package com.vens.producer;

import javax.jms.Message;

public interface Producer {
    Message produceMessage();
    Message sendMessage();
}

package com.vens.activemq.pro_cus;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author LuZhiqing
 * @Description:
 * @date 2018/12/5
 */
public class Recevider {

    public static void main(String[] args) throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                ActiveMQConnectionFactory.DEFAULT_USER,
                ActiveMQConnectionFactory.DEFAULT_PASSWORD,
                "tcp://localhost:61616"
        );
        Connection connection = connectionFactory.createConnection();
        connection.start();
        //第一个参数是否启动事务,第二参数设置签收模式
        Session session = connection.createSession(Boolean.FALSE, Session.CLIENT_ACKNOWLEDGE);
        Destination destination = session.createQueue("queueName");


        MessageConsumer messageConsumer = session.createConsumer(destination);
        while(true){
            TextMessage message=(TextMessage)messageConsumer.receive();
            //手工签收消息，另起一个线程去通知mq,确认签收
            message.acknowledge();
            if(message==null){
                break;
            }
            System.out.println("接收到消息:"+message.getText());
        }

        if(connection!=null){
            connection.close();
        }
    }
}

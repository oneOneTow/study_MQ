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
        //��һ�������Ƿ���������,�ڶ���������ǩ��ģʽ
        Session session = connection.createSession(Boolean.FALSE, Session.CLIENT_ACKNOWLEDGE);
        Destination destination = session.createQueue("queueName");


        MessageConsumer messageConsumer = session.createConsumer(destination);
        while(true){
            TextMessage message=(TextMessage)messageConsumer.receive();
            //�ֹ�ǩ����Ϣ������һ���߳�ȥ֪ͨmq,ȷ��ǩ��
            message.acknowledge();
            if(message==null){
                break;
            }
            System.out.println("���յ���Ϣ:"+message.getText());
        }

        if(connection!=null){
            connection.close();
        }
    }
}

package com.vens.activemq.pro_cus;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author LuZhiqing
 * @Description:
 * @date 2018/12/5
 */
public class Sender {
    public static void main(String[] args) throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                ActiveMQConnectionFactory.DEFAULT_USER,
                ActiveMQConnectionFactory.DEFAULT_PASSWORD,
                "tcp://localhost:61616"
        );
        Connection connection = connectionFactory.createConnection();
        connection.start();
        //��һ�������Ƿ���������,�ڶ���������ǩ��ģʽ
        //Session session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
        //ʹ������
        Session session = connection.createSession(Boolean.TRUE, Session.CLIENT_ACKNOWLEDGE);

        Destination destination = session.createQueue("queueName");
        MessageProducer messageProducer = session.createProducer(destination);
        //���ó־û�����
        messageProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

        for(int i=0;i<5;i++){
            TextMessage textMessage = session.createTextMessage();
            textMessage.setText("i am message id is "+i);
            messageProducer.send(textMessage);
        }
        System.out.println("�����߷�����Ϣ");
        //�ύ����
        session.commit();
        if(connection != null){
            connection.close();
        }

    }
}

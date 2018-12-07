package com.vens.producer;

import com.vens.activemq.model.MailModel;
import com.vens.producer.mq.AmpProducerImpl;
import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;

/**
 * @author LuZhiqing
 * @Description:
 * @date 2018/12/7
 */
public class ProducerApp {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext(new String[]{"appContext.xml"});
        context.start();
        AmpProducerImpl amq=(AmpProducerImpl)context.getBean("ampProducerImpl");
        for(int i=0;i<9;i++){
            amq.sendMessage(new MailModel("18385067722@163.com",
                    "2319687449@qq.com",
                    "amq_test",
                    "hello world "+i));
        }

        System.out.println("producer start");
    }
}

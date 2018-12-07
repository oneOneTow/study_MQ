package com.vens.consumer;

import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;

/**
 * @author LuZhiqing
 * @Description:
 * @date 2018/12/7
 */
public class ConsumerApp {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext(new String[]{"appContext.xml"});
        context.start();
        System.out.println("consumer start");
    }
}

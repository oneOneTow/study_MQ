package com.vens.activemq.model;

/**
 * @author LuZhiqing
 * @Description:
 * @date 2018/12/5
 */
public class MessageModel {
    private long timeTrap;

    public MessageModel() {
        this.timeTrap=System.currentTimeMillis();
    }

    public long getTimeTrap() {
        return timeTrap;
    }

    public void setTimeTrap(long timeTrap) {
        this.timeTrap = timeTrap;
    }
}

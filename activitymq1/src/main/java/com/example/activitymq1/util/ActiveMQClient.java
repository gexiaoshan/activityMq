package com.example.activitymq1.util;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 * @author gexiaoshan
 * @description: TODO
 * @date 2019/4/28 15:10
 */
@Component
public class ActiveMQClient {

    @Autowired
    @Qualifier("myJmsTemplate")
    private JmsTemplate jmsTemplate;

    @JmsListener(destination = "test1_reply_ge")
    public void consumerMessage(String text) {
        System.out.println("从test1_reply_ge队列收到的回复报文为:" + text);
    }

    //无返回消息-异步发送
    public void send(String message) {
        jmsTemplate.convertAndSend("test1_ge", message);
    }

    //发送信息并获取返回信息-同步发送
    public Message sendWithReceive(String destinationName, String message) {

        return jmsTemplate.sendAndReceive(destinationName, new MessageCreator() {
                    @Override
                    public Message createMessage(Session session) throws JMSException {
                        //会创建一个临时的Queue，通过这个零时的队列接收返回消息
                        Message msg = session.createTextMessage(message);
                        return msg;
                    }
                }
        );
    }

}

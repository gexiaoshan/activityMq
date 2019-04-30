package com.example.activitymq2.util;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.TextMessage;

/**
 * @author gexiaoshan
 * @description: TODO
 * @date 2019/4/28 15:14
 */
@Component
public class ActiveMQServer {

    @Autowired
    private JmsTemplate jmsTemplate;

    //接收消息，并回复消息
    @JmsListener(destination = "test1_ge")
    @SendTo("test1_reply_ge")
    public String receive(String message) {
        System.out.println("收到的 message 是：" + message);
        return message + "reply";
    }

    @JmsListener(destination = "test2_ge")
    public void receive(Message message) throws Exception {

        TextMessage msg = (TextMessage) message;

        System.out.println("收到的 message 是：" + message);
        System.out.println("收到消息：" + msg.getText());

        Destination destination = msg.getJMSReplyTo();
        System.out.println("Reply_destination:" + destination);
        jmsTemplate.convertAndSend(destination, msg.getText() + "_reply");
    }
}

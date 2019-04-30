package com.example.activitymq1;

import com.example.activitymq1.util.ActiveMQClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import javax.jms.Message;
import javax.jms.TextMessage;

@SpringBootApplication
public class Activitymq1Application {

    @Autowired
    ActiveMQClient client;

    public static void main(String[] args) {
        SpringApplication.run(Activitymq1Application.class, args);
    }

//      异步接收消息
//    @PostConstruct
//    public void init() {
//        StopWatch stopWatch = new StopWatch();
//        stopWatch.start();
//        for (int i = 0; i < 100; i++) {
//            client.send("发送消息----test_ge-----");
//        }
//        stopWatch.stop();
//        System.out.println("发送消息耗时: " + stopWatch.getTotalTimeMillis());
//    }

    //同步接收消息
    @PostConstruct
    public void init2() throws Exception {

        for (int i = 0; i < 100; i++) {
            String reqMsg = "test2_ge_发送消息_" + i;
            System.out.println("发送消息：" + reqMsg);

            Message message = client.sendWithReceive("test2_ge", reqMsg);
            TextMessage msg = (TextMessage) message;

            System.out.println("reply message:" + message);
            System.out.println("回复数据：" + (message != null ? msg.getText() : null));
        }
    }

}

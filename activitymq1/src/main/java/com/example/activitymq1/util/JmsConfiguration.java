package com.example.activitymq1.util;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;

/**
 * @author gexiaoshan
 * @description: TODO
 * @date 2019/4/29 11:30
 */
@Configuration
public class JmsConfiguration {

    @Value("${spring.activemq.broker-url}")
    private String brokerUrl;
    @Value("${spring.activemq.password}")
    private String password;
    @Value("${spring.activemq.user}")
    private String userName;

    @Bean(name = "connectionFactory")
    public ActiveMQConnectionFactory getFirstConnectionFactory() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL(brokerUrl);
        connectionFactory.setUserName(userName);
        connectionFactory.setPassword(password);
        return connectionFactory;
    }

    @Bean(name = "myJmsTemplate")
    public JmsTemplate getFirstJmsTemplate(@Qualifier("connectionFactory") ConnectionFactory connectionFactory) {
        JmsTemplate template = new JmsTemplate(connectionFactory);
        template.setReceiveTimeout(5000);//设置sendAndReceive超时时间5s，超时返回null
        return template;
    }


}

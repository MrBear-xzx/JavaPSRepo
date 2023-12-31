package com.ps.activemq.produce;

import com.ps.activemq.constant.Constant;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JmsProduce {

    public static void main(String[] args) throws JMSException {
        // 1. 创建连接工厂，按照给定的url地址，采用默认的用户名和密码
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(Constant.ACTIVE_MQ_URL);
        // 2. 通过连接工厂，获得连接connection
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        // 3. 创建会话session
        // 两个参数，第一个叫事务/第二个叫签收
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 4. 创建目的地（具体是队列还是主题）
        Queue queue = session.createQueue(Constant.QUEUE_NAME_01);
        // 5. 创建消息的生产者
        MessageProducer messageProducer = session.createProducer(queue);
        // 6. 通过使用messageProducer生产3条消息发送到MQ的队列里面
        for (int i = 0; i < 3; i++) {
            // 7. 创建消息
            TextMessage textMessage = session.createTextMessage("msg---" + i);
            // 8. 通过messageProducer发送给mq
            messageProducer.send(textMessage);
        }
        // 9. 关闭资源
        messageProducer.close();
        session.close();
        connection.close();

        System.out.println("*****消息发布到MQ完成");
    }

}

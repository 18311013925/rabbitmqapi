package rabbitmq.api.topic_exchange;

import com.rabbitmq.client.*;
import rabbitmq.api.utils.RabbitClientUtils;

public class ConsumerTopicExchange {



    public static void main(String[] args) throws Exception {

        // 2通过连接工厂创建连接
        Connection connection = RabbitClientUtils.getConnection();
        // 3通过connection 创建一个Channel
        Channel channel = connection.createChannel();

        // 4 声明
        String exchangeName = "test_topic_exchange";
        String exchangeType = "topic";
        String queueName = "test_topic_queue";
        // * 只匹配一个单词， user.name
//         String routingKey = "user.*";
        String routingKey = "user.#"; // 设置路由键
        // # 匹配多个单词， user.name.address  ...
        // 1. 声明交换机
        channel.exchangeDeclare(exchangeName, exchangeType, true, false, null);
        // 2，声名一个队列
        channel.queueDeclare(queueName, false, false, false, null);
        // 3、建立绑定关系
        channel.queueBind(queueName,exchangeName, routingKey);
//        channel.queueUnbind(queueName, exchangeName, routingKey);
        // 5.消息是否持久化
        QueueingConsumer queueingConsumer = new QueueingConsumer(channel);
        // 6 设置channel
        channel.basicConsume(queueName, true, queueingConsumer);

        while (true) {
            //nextDelivery() 一个有参数，一个无参数 ， 无参数会一直阻塞
            // 7 获取消息， 如果没有消息，这一步将会一直阻塞
            QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();
            String msg = new String(delivery.getBody());
            System.out.println("消费端: " + msg);
            Envelope envelope = delivery.getEnvelope();
        }
    }
}
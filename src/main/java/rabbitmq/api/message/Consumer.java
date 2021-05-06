package rabbitmq.api.message;

import com.rabbitmq.client.*;
import rabbitmq.api.utils.RabbitClientUtils;

import java.util.Map;

public class Consumer {

    public static void main(String[] args) throws Exception {

        // 2通过连接工厂创建连接
        Connection connection = RabbitClientUtils.getConnection();
        // 3通过connection 创建一个Channel
        Channel channel = connection.createChannel();
        // 4 声明一个队列
        String queueName = "test001";
        channel.queueDeclare(queueName, true, false, false, null);

        // 5.创建消费者
        QueueingConsumer queueingConsumer = new QueueingConsumer(channel);
        // 6 设置channel
        channel.basicConsume(queueName, true, queueingConsumer);

        while (true) {
            //nextDelivery() 一个有参数，一个无参数 ， 无参数会一直阻塞
            // 7 获取消息
            QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();
            String msg = new String(delivery.getBody());
            System.out.println("消费端: " + msg);
            AMQP.BasicProperties properties = delivery.getProperties();
            Map<String, Object> headers = properties.getHeaders();
            Object my1 = headers.get("my1");
            Object my2 = headers.get("my2");
            System.out.println("headers get value"+my1 + my2);
            Envelope envelope = delivery.getEnvelope();
        }
    }
}
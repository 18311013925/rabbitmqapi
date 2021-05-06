package rabbitmq.api.limit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import rabbitmq.api.utils.RabbitClientUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Auther: lizhi
 * @Date: 2018/9/28 19:25
 * @Description:
 */
public class Consumer {

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {

        Connection connection = RabbitClientUtils.getConnection();
        Channel channel = connection.createChannel();
        String exchangeName = "test_qos_exchange";
        String routingKey = "qos.#";
        String queueName = "test_qos_queue";
        channel.exchangeDeclare(exchangeName, "topic", true);
        channel.queueDeclare(queueName, true, false, false, null);
        channel.queueBind(queueName, exchangeName, routingKey);

        //prefetchCount 消费端最大消费数量， 1为一条一条的处理
        channel.basicQos(0, 1, false);
        // 1. 限流方式； autoAck false, 签收方式设置为手动签收
        channel.basicConsume(queueName, false , new MyConsumer(channel));
    }
}

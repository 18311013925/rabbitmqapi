package rabbitmq.api.ack;

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

        String exchangeName = "test_ack_exchange";
        String routingKey = "ack.#";
        String queueName = "test_ack_queue";
        channel.exchangeDeclare(exchangeName, "topic", true);
        channel.queueDeclare(queueName, true, false, false, null);
        channel.queueBind(queueName, exchangeName, routingKey);

        // 1. 手工签收，必须关闭autoAck = false;
        channel.basicConsume(queueName, false , new MyConsumer(channel));
    }
}

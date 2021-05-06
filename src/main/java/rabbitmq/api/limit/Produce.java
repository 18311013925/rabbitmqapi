package rabbitmq.api.limit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import rabbitmq.api.utils.RabbitClientUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Auther: lizhi
 * @Date: 2018/9/28 19:24
 * @Description:
 */
public class Produce {

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = RabbitClientUtils.getConnection();
        Channel channel = connection.createChannel();
        String exchangeName = "test_qos_exchange";
        String routingKey = "qos.save";

        // 5 发送一条消息
        String msg = "Hello RabbitMq send qos message";
        for (int i = 0; i < 5; i++) {
            channel.basicPublish(exchangeName, routingKey, true,null, msg.getBytes());
        }
    }
}

package rabbitmq.api.dlx;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import rabbitmq.api.utils.RabbitClientUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
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
        String exchangeName = "test_dlx_exchange";
        String routingKey = "dlx.save";
        String msg = "Hello RabbitMq Send DLX SMessage" ;
        AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                .deliveryMode(2)
                .contentEncoding("UTF-8")
                .expiration("10000")
                .build();
        channel.basicPublish(exchangeName, routingKey, true, properties, msg.getBytes());
    }
}

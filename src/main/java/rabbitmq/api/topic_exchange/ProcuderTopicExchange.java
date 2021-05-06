package rabbitmq.api.topic_exchange;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import rabbitmq.api.utils.RabbitClientUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;


public class ProcuderTopicExchange {

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = RabbitClientUtils.getConnection();
        // 3通过connection 创建一个Channel
        Channel channel = connection.createChannel();
        //4、声名
        String exchangeName = "test_fanout_exchange";
        String routingKey1 = "user.save";
        String routingKey2 = "user.update";
        String routingKey3 = "user.delete.abc";
        // 5 、发送
        String msg = "hello World RabbitMq 4 fanout Exchange Message ...";
        channel.basicPublish(exchangeName, routingKey1, null, msg.getBytes());
        channel.basicPublish(exchangeName, routingKey2, null, msg.getBytes());
        channel.basicPublish(exchangeName, routingKey3, null, msg.getBytes());
    }
}

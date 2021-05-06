package rabbitmq.api.fanoutExchange;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import rabbitmq.api.utils.RabbitClientUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;


public class ProcuderFanoutExchange {

    public static void main(String[] args) throws IOException, TimeoutException {
        // 2通过连接工厂创建连接
        Connection connection = RabbitClientUtils.getConnection();
        // 3通过connection 创建一个Channel
        Channel channel = connection.createChannel();
        //4、声名
        String exchangeName = "test_fanout_exchange";
        // 5 、发送
        String msg = "hello World RabbitMq 4 fanout Exchange Message ...";
        for (int i = 0; i < 10; i++) {
            channel.basicPublish(exchangeName, "aaaa", null, msg.getBytes());
        }
    }
}

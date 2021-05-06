package rabbitmq.api.quickstart;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import rabbitmq.api.utils.RabbitClientUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;


public class Procuder {

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = RabbitClientUtils.getConnection();
        // 3通过connection 创建一个Channel
        Channel channel = connection.createChannel();
        // 4通过Channel发送数据
        for (int i = 0; i < 5; i++) {
            String msg = "Hello RabbitMQ";
            channel.basicPublish("", "test001", null, msg.getBytes());
        }


    }
}

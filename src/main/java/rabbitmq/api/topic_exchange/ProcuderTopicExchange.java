package rabbitmq.api.topic_exchange;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;


public class ProcuderTopicExchange {

    public static void main(String[] args) throws IOException, TimeoutException {
        // 创建一个ConnectionFactory 工厂， 并进行配置
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setVirtualHost("/");
        Connection connection = null;
        Channel channel = null;

        try {
            // 2通过连接工厂创建连接
            connection = connectionFactory.newConnection();
            // 3通过connection 创建一个Channel
            channel = connection.createChannel();
            //4、声名
            String exchangeName = "test_fanout_exchange";
            String routingKey1 = "user.save";
            String routingKey2 = "user.update";
            String routingKey3 = "user.delete.abc";
            // 5 、发送
            String msg = "hello World RabbitMq 4 fanout Exchange Message ...";
            channel.basicPublish(exchangeName, routingKey1 , null , msg.getBytes());
            channel.basicPublish(exchangeName, routingKey2 , null , msg.getBytes());
            channel.basicPublish(exchangeName, routingKey3 , null , msg.getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } finally {
//            记得关闭相关连接
            if (channel != null) {
                channel.close();
            }
            if (connection != null) {
                connection.close();
            }
        }


    }
}

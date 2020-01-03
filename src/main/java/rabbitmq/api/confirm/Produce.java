package rabbitmq.api.confirm;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Auther: lizhi
 * @Date: 2018/9/28 19:24
 * @Description:
 */
public class Produce {

    public static void main(String[] args) throws IOException, TimeoutException {
        // 1.创建ConnectionFactory
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");

        // 2. 获取Connection
        Connection connection = connectionFactory.newConnection();

        //3. 通过Connection 创建一个新的Channel
        Channel channel = connection.createChannel();

        // 4 指定消息投递模式,：消息的确认的模式
        channel.confirmSelect();

        String exchangeName = "test_confirm_exchange";
        String routingKey = "confirm.save";

        // 5 发送一条消息
        String msg = "Hello RabbitMq sen confirm message";
        channel.basicPublish(exchangeName, routingKey, null, msg.getBytes());

        // 6. 添加一个监听
        channel.addConfirmListener(new ConfirmListener() {

            // 成功
            // 关键的唯一的消息标签deliveryTag， multiple ：是否批量， 暂时不用管
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("成功 Ack");
            }

            // 失败， 比如磁盘写满了，mq 出现一些异常， key 容量达到上限，也有可能handleAck，handleNack 都没有收到， 进行抓取，和重发
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("失败 Nack");
            }
        });


    }
}

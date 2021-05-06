package rabbitmq.api.dlx;

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
 * @Date: 2018/9/28 19:25
 * @Description:
 */
public class Consumer {

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = RabbitClientUtils.getConnection();
        Channel channel = connection.createChannel();
        // 这就是一个普通的交换机和队列，交换机
        String exchangeName = "test_dlx_exchange";
        String routingKey = "dlx.#";
        String queueName = "test_dlx_queue";
        channel.exchangeDeclare(exchangeName, "topic", true);
        Map<String, Object> agruments = new HashMap<String, Object>();
        agruments.put("x-dead-letter-exchange","dlx.exchange");

        channel.queueDeclare(queueName, true, false, false, agruments);
        channel.queueBind(queueName, exchangeName, routingKey);

        // 要进行死信队列的声明,绑定
        channel.exchangeDeclare("dlx.exchange", "topic", true, false, null);
        channel.queueDeclare("dlx.queue",true, false, false, null);
        channel.queueBind("dlx.queue", "dlx.exchange","#");

        // 1. 手工签收，必须关闭autoAck = false;
        channel.basicConsume(queueName, true , new MyConsumer(channel));
    }
}

package rabbitmq.api.confirm;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
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


        // 2. 获取Connection
        Connection connection = RabbitClientUtils.getConnection();

        //3. 通过Connection 创建一个新的Channel
        Channel channel = connection.createChannel();


        String exchangeName = "test_confirm_exchange";
        String routingKey = "confirm.*";

//        AMQP.Exchange.DeclareOk exchangeDeclare(String exchange, String type, boolean durable)
        // 4.声明一个交换机和队列， 然后进行绑定设置，最后指定路由key
        channel.exchangeDeclare(exchangeName, "topic", true);

        //Queue.DeclareOk queueDeclare(String queue, boolean durable, boolean exclusive, boolean autoDelete,
        //                                 Map<String, Object> arguments) throws IOException;
        //queue：队列名称， durable：是否持久话；arguments：扩展参数
        String queueName = "test_confirm_querue";
        channel.queueDeclare(queueName, true, false, false, null);
        channel.queueBind(queueName, exchangeName, routingKey);

        // 5, 创建消费者, 指定Consumer的channel
        QueueingConsumer queueingConsumer = new QueueingConsumer(channel);
        // 设置消费者， 指定消费的队列， autoAck: 自动签收， callback：queueingConsumer
        channel.basicConsume(queueName, true, queueingConsumer);

        while (true){
            QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();
            System.out.println("消费端" + new String(delivery.getBody()));
        }
    }
}

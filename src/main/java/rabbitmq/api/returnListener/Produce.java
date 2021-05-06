package rabbitmq.api.returnListener;

import com.rabbitmq.client.*;
import rabbitmq.api.utils.RabbitClientUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Auther: lizhi
 * @Date: 2018/9/28 19:24
 * @Description:
 */
public class Produce {

    /**
     * @param args
     * @throws IOException
     * @throws TimeoutException
     */
    public static void main(String[] args) throws IOException, TimeoutException {


        Connection connection = RabbitClientUtils.getConnection();
        Channel channel = connection.createChannel();

        String exchangeName = "test_return_exchange";
//        String routingKey = "return.save";
        String routingKeyError = "abc.save";

        channel.addReturnListener(new ReturnListener() {
            /*
             * @param replyCode 响应吗， 路由成没成功
             * @param replyText  回复内容
             * @param exchange
             * @param routingKey
             * @param properties   AMQP.BasicProperties properties,
             * @param body 实际的消息体内容
             * @throws IOException 跑出ioexception 异常
             */

            public void handleReturn(int replyCode, String replyText, String exchange, String routingKey, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("handle return");
                System.out.println("replyCode: " + replyCode);
                System.out.println("exchange: " + exchange);
                System.out.println("routingKey: " + routingKey);
                System.out.println("properties: " + properties.toString());
                System.out.println("body: " + new String(body));
            }
        });
        String msg = "Hello RabbitMQ return Message";
//        Mandatory：强制性的，如果为true, 则监听器会接收到路由不可达的消息，然后进行后续处理， 如果为false, 那么broker端自动删除该消息；
        channel.basicPublish(exchangeName, routingKeyError, true, null, msg.getBytes());


    }
}

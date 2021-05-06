package rabbitmq.api.ack;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @Auther: lizhi
 * @Date: 2018/9/29 11:21
 * @Description:
 */
public class MyConsumer extends DefaultConsumer {


    private Channel channel;

    public MyConsumer(Channel channel) {
        super(channel);
        this.channel = channel;
    }

    @Override
    public void handleDelivery(String consumerTag, // 消费者标签, 自动生成的
                               Envelope envelope, //
                               AMQP.BasicProperties properties,
                               byte[] body)
            throws IOException {
        System.out.println("---handleDelivery----");
        System.out.println("body" + new String(body));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if ((Integer) properties.getHeaders().get("num") == 0) {
            // 一般requeue 设置为false, 如果消息失败，会把这条消息添加到队列的尾端， 如果一直失败， 一直尝试
            channel.basicNack(envelope.getDeliveryTag(), false, true);
        } else {
            //long deliveryTag,
            // boolean multiple, 是否批量删除, false 不支持批量签收
            //basicAck 主动给broker 推送消息
            long deliveryTag = envelope.getDeliveryTag();
            channel.basicAck(deliveryTag, false);
        }

    }

}

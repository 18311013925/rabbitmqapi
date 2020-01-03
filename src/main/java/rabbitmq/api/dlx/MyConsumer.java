package rabbitmq.api.dlx;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

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

    }

}

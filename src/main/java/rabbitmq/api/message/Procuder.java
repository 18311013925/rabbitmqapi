package rabbitmq.api.message;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import rabbitmq.api.utils.RabbitClientUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;


public class Procuder {

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = RabbitClientUtils.getConnection();

        // 2通过连接工厂创建连接
        // 3通过connection 创建一个Channel
        Channel channel = connection.createChannel();
        // 4通过Channel发送数据
        Map<String, Object> headers = new HashMap<String, Object>();
        headers.put("my1", "1111");
        headers.put("my2", "2222");
        AMQP.BasicProperties properties = new AMQP.BasicProperties().builder()
                .deliveryMode(2) // 传送方式
                .contentEncoding("UTF-8") // 编码方式
                .expiration("10000") // 过期时间
                .headers(headers) //自定义属性
                .build();

        for (int i = 0; i < 5; i++) {
            String msg = "Hello RabbitMQ";
            channel.basicPublish("", "test001", properties, msg.getBytes());
        }
    }
}

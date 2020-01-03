package rabbitmq.api.message;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;


public class Procuder {

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


            for (int i = 0 ; i < 5 ; i++){
                String msg = "Hello RabbitMQ";
                channel.basicPublish("", "test001", properties, msg.getBytes());
            }
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

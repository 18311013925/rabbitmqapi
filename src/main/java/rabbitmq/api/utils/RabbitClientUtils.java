package rabbitmq.api.utils;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author lizhi
 * @date: 2021/5/6 9:52
 */
public class RabbitClientUtils {

    public static Connection getConnection() throws IOException, TimeoutException {
        // 创建一个ConnectionFactory 工厂， 并进行配置
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setVirtualHost("/");
        return connectionFactory.newConnection();
    }

}

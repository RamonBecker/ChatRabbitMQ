
package br.edu.ifsc.canoinhas.ads.rabbitmq;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

public class Emissorps {
    // mvn -q compile exec:java@first-execution
    private static final String EXCHANGE_NAME = "ps1";
    public final static String USER = "becker";
    public final static String PASSWD = "becker";
    private final static String VHOST = "/";
    public final static String HOST = "localhost";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(HOST);
        factory.setUsername(USER);
        factory.setPassword(PASSWD);
        factory.setVirtualHost(VHOST);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);

        String message = "Hello world 2!";

        channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes("UTF-8"));
        System.out.println(" Mensagem enviada para o grupo " + EXCHANGE_NAME);

        channel.close();
        connection.close();
    }

}

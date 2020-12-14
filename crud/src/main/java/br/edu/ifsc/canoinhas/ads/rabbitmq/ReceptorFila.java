
package br.edu.ifsc.canoinhas.ads.rabbitmq;

import com.rabbitmq.client.*;

import java.io.IOException;

public class ReceptorFila {

    private final static String QUEUE_NAME = "luciano";
    private final static String VHOST = "/";
    public final static String USER = "admin";
    public final static String PASSWD = "ads2020";
    public final static String HOST = "localhost";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(HOST);
        factory.setUsername(USER);
        factory.setPassword(PASSWD);
        factory.setVirtualHost(VHOST);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println("Aguardando mensagens da fila " + QUEUE_NAME);

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                    byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" Mensagem: " + message);
            }
        };
        channel.basicConsume(QUEUE_NAME, true, consumer);
    }
}

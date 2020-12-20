package br.com.ifsc.crud.controllers;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import br.com.ifsc.crud.entities.User;

public class ControllerReceptorGrupo {
	private static String EXCHANGE_NAME;
	private static User user;
	public final static String HOST = "localhost";
	private final static String VHOST = "/";
	private Connection connection;
	private Channel channel;

	public void iniciarReceptorGrupo() {
		try {
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost(HOST);
			factory.setUsername(user.getUsername());
			factory.setPassword(user.getPassword());
			factory.setVirtualHost(VHOST);

			connection = factory.newConnection();

			channel = connection.createChannel();

			channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
			String queueName = channel.queueDeclare().getQueue();
			channel.queueBind(queueName, EXCHANGE_NAME, "");

			System.out.println(" Aguardando mensagens do tópico " + EXCHANGE_NAME);

			Consumer consumer = new DefaultConsumer(channel) {
				@Override
				public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
						byte[] body) throws IOException {
					String message = new String(body, "UTF-8");
					System.out.println(" Mensagem: " + message);
				}
			};
			channel.basicConsume(queueName, true, consumer);
		} catch (IOException | TimeoutException e) {
			e.printStackTrace();
		}
	}

}

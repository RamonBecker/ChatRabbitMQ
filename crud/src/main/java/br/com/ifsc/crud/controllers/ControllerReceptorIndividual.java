package br.com.ifsc.crud.controllers;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import br.com.ifsc.crud.entities.User1;
import br.com.ifsc.crud.utility.MessageAlert;

public class ControllerReceptorIndividual {
	private static String QUEUE_NAME;
	private static String VHOST = "/";
	private static User1 user;
	private static String HOST = "localhost";
	private String mensagem;

	private static ConnectionFactory factory;
	private Connection connection;
	private static Channel channel;

	public ControllerReceptorIndividual() {
		factory = new ConnectionFactory();
	}

	public void IniciarReceptorIndividual() {

		try {
			factory.setHost(HOST);
			factory.setUsername(user.getUsername());
			factory.setPassword(user.getPassword());
			factory.setVirtualHost(VHOST);
			connection = factory.newConnection();
			channel = connection.createChannel();

			channel.queueDeclare(QUEUE_NAME, false, false, false, null);

			System.out.println("Aguardando mensagens da fila " + QUEUE_NAME);

			Consumer consumer = new DefaultConsumer(channel) {
				@Override
				public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
						byte[] body) throws IOException {
					mensagem = new String(body, "UTF-8");
					System.out.println(" Mensagem: " + mensagem);
				}
			};
			channel.basicConsume(QUEUE_NAME, true, consumer);

		} catch (IOException | TimeoutException e) {
			e.printStackTrace();
		}

	}

	public void encerrarConexao() {
	     try {
			channel.close();
		    connection.close();
		} catch (IOException | TimeoutException e) {
			MessageAlert.mensagemErro("Erro ao fechar conexão");
			e.printStackTrace();
		}
	  
	}
	
	public static String getQUEUE_NAME() {
		return QUEUE_NAME;
	}

	public static void setQUEUE_NAME(String qUEUE_NAME) {
		if(qUEUE_NAME == null || qUEUE_NAME.isBlank()) {
			throw new IllegalArgumentException("O nome da fila não pode ser vazio");
		}
		QUEUE_NAME = qUEUE_NAME;
	}

	public static User1 getUser() {
		return user;
	}

	public static void setUser(User1 user) {
		if(user == null) {
			throw new IllegalArgumentException("O usuário não pode ser nulo!");
		}
		ControllerReceptorIndividual.user = user;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		if(mensagem == null || mensagem.isBlank()) {
			throw new IllegalArgumentException("");
		}
		this.mensagem = mensagem;
	}


}

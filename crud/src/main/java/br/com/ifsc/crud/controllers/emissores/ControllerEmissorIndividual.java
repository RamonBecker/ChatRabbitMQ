package br.com.ifsc.crud.controllers.emissores;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import br.com.ifsc.crud.controllersViews.MensagemIndividualController;
import br.com.ifsc.crud.entities.User;

public class ControllerEmissorIndividual {
	private static String QUEUE_NAME;
	private final static String VHOST = "/";
	private static User user;
	public final static String HOST = "localhost";
	private ConnectionFactory factory;
	private Channel channel;
	private Connection connection;
	private String mensagem;
	private MensagemIndividualController mensagemIndividualController;

	public void enviarMensagem() {

		try {
			factory = new ConnectionFactory();
			factory.setHost(HOST);
			factory.setUsername(user.getUsername());
			factory.setPassword(user.getPassword());
			factory.setVirtualHost(VHOST);

			connection = factory.newConnection();

			channel = connection.createChannel();

			channel.queueDeclare(QUEUE_NAME, false, false, false, null);
			channel.basicPublish("", QUEUE_NAME, null, mensagem.getBytes("UTF-8"));
			System.out.println("Mensagem enviada para a fila " + QUEUE_NAME);
			channel.close();
			connection.close();
		} catch (IOException | TimeoutException e) {
			e.printStackTrace();
		}
	}

	public static String getQUEUE_NAME() {
		return QUEUE_NAME;
	}

	public static void setQUEUE_NAME(String qUEUE_NAME) {
		if (qUEUE_NAME == null || qUEUE_NAME.isBlank()) {
			throw new IllegalArgumentException("A fila não pode ser vazia!");
		}
		QUEUE_NAME = qUEUE_NAME;
	}

	public static User getUser() {
		return user;
	}

	public static void setUser(User user) {
		if (user == null) {
			throw new IllegalArgumentException("O usuário não pode ser vazio!");
		}
		ControllerEmissorIndividual.user = user;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		if (mensagem == null || mensagem.isBlank()) {
			throw new IllegalArgumentException("A mensagem não pode ser vazia!");
		}
		this.mensagem = mensagem;
	}

	public MensagemIndividualController getMensagemIndividualController() {
		return mensagemIndividualController;
	}

	public void setMensagemIndividualController(MensagemIndividualController mensagemIndividualController) {
		this.mensagemIndividualController = mensagemIndividualController;
	}

}

package br.com.ifsc.crud.controllers.emissores;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import br.com.ifsc.crud.entities.User;

public class ControllerEmissorGrupo {
	private String EXCHANGE_NAME;
	private final static String VHOST = "/";
	public final static String HOST = "localhost";
	private static User user;
	private String mensagem;
	private Connection connection;
	private Channel channel;

	public void enviarMensagem() {
		try {
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost(HOST);
			factory.setUsername(user.getUsername());
			factory.setPassword(user.getPassword());
			factory.setVirtualHost(VHOST);

			connection = factory.newConnection();
			channel = connection.createChannel();
			channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
			channel.basicPublish(EXCHANGE_NAME, "", null, mensagem.getBytes("UTF-8"));
			System.out.println(" Mensagem enviada para o grupo " + EXCHANGE_NAME);

			channel.close();
			connection.close();

		} catch (IOException | TimeoutException e) {
			e.printStackTrace();
		}
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public String getEXCHANGE_NAME() {
		return EXCHANGE_NAME;
	}

	public void setNomeFila(String nomeFila) {
		if (nomeFila == null || nomeFila.isBlank()) {
			throw new IllegalArgumentException("O nome da fila não pode ser vazia!");
		}
		EXCHANGE_NAME = nomeFila;
	}

	public void setUser(User user) {
		if (user == null) {
			throw new IllegalArgumentException("O usuário não pode ser vazio!");
		}
		ControllerEmissorGrupo.user = user;
	}

	public static User getUser() {
		return user;
	}

}

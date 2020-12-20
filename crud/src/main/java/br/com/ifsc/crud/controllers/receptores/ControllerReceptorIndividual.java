package br.com.ifsc.crud.controllers.receptores;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import br.com.ifsc.crud.controllersViews.MensagemIndividualController;
import br.com.ifsc.crud.entities.User;
import br.com.ifsc.crud.utility.MessageAlert;

public class ControllerReceptorIndividual extends Thread {
	private static String QUEUE_NAME;
	private static final String VHOST = "/";
	private static User user;
	private static User contato;
	private static final String HOST = "localhost";
	private static String mensagem;
	private static ConnectionFactory factory;
	private static Connection connection;
	private static Channel channel;
	private static MensagemIndividualController mensagemIndividualController;

	public ControllerReceptorIndividual() {
		factory = new ConnectionFactory();
	}

	public void run() {

		iniciarReceptorIndividual();

	}

	public void iniciarReceptorIndividual() {

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

					if (getMensagemIndividualController().getTxtAreaMensagem().getText().isBlank()) {
						getMensagemIndividualController().getTxtAreaMensagem().setText(

								getContato().getUsername() + " enviou:" + mensagem);
					} else {

						getMensagemIndividualController().getTxtAreaMensagem()
								.setText(getMensagemIndividualController().getTxtAreaMensagem().getText() + "\n"
										+ getContato().getUsername() + " respondeu:" + mensagem);

					}

				}

			};

			channel.basicConsume(QUEUE_NAME, true, consumer);

		} catch (IOException | TimeoutException e) {
			e.printStackTrace();
		}

	}

	
	public void fecharConexao() {
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
		if (qUEUE_NAME == null || qUEUE_NAME.isBlank()) {
			throw new IllegalArgumentException("O nome da fila não pode ser vazio");
		}
		QUEUE_NAME = qUEUE_NAME;
	}

	public static User getUser() {
		return user;
	}

	public static void setUser(User user) {
		if (user == null) {
			throw new IllegalArgumentException("O usuário não pode ser nulo!");
		}
		ControllerReceptorIndividual.user = user;
	}

	public static String getMensagem() {
		return mensagem;
	}

	public static void setMensagem(String mensagem) {
		if (mensagem == null || mensagem.isBlank()) {
			throw new IllegalArgumentException("A mensagem recebida é vazia");
		}
		ControllerReceptorIndividual.mensagem = mensagem;
	}

	public static MensagemIndividualController getMensagemIndividualController() {
		return mensagemIndividualController;
	}

	public static void setMensagemIndividualController(MensagemIndividualController mensagemIndividualController) {
		ControllerReceptorIndividual.mensagemIndividualController = mensagemIndividualController;
	}

	public static User getContato() {
		return contato;
	}

	public static void setContato(User contato) {
		if (contato == null) {
			throw new IllegalArgumentException("O contato não pode ser vazio");
		}
		ControllerReceptorIndividual.contato = contato;
	}

}

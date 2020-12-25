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

import br.com.ifsc.crud.controllers.ControllerUser;
import br.com.ifsc.crud.controllersViews.MensagemIndividualController;
import br.com.ifsc.crud.controllersViews.PrincipalController;
import br.com.ifsc.crud.entities.User;
import br.com.ifsc.crud.utility.MessageAlert;
import javafx.application.Platform;

public class ControllerReceptorIndividual {
	private String QUEUE_NAME;
	private static final String VHOST = "/";
	private User user;
	private User contato;
	private static final String HOST = "localhost";
	private static String mensagem;
	private static ConnectionFactory factory;
	private static Connection connection;
	private static Channel channel;
	private MensagemIndividualController mensagemIndividualController;
	private PrincipalController principalController;
	private ControllerUser controllerUser;
	private static String resposta;

	public ControllerReceptorIndividual() {
		factory = new ConnectionFactory();
		controllerUser = ControllerUser.getInstance();
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
					String[] mensagemSeparada = mensagem.split(";");

					resposta = mensagemSeparada[0] + " enviou " + mensagemSeparada[1];

					user.getFilaMensagemIndividual().put(getQUEUE_NAME(), resposta);
					contato.getFilaMensagemIndividual().put(getQUEUE_NAME(), resposta);
					controllerUser.getListUser().put(user.getUsername(), user);
					controllerUser.getListUser().put(contato.getUsername(), contato);

					if (mensagemIndividualController != null) {

						if (mensagemIndividualController.getTxtAreaMensagem().getText().isBlank()) {
							mensagemIndividualController.getTxtAreaMensagem().setText(resposta);
						} else {

							mensagemIndividualController.getTxtAreaMensagem().setText(
									mensagemIndividualController.getTxtAreaMensagem().getText() + "\n" + resposta);
						}
					}
					Platform.runLater(() -> {
						MessageAlert.mensagemRealizadoSucesso("Usuário:" + user.getUsername() + "\n" + resposta);

					});

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

	public String getQUEUE_NAME() {
		return QUEUE_NAME;
	}

	public void setQUEUE_NAME(String qUEUE_NAME) {
		if (qUEUE_NAME == null || qUEUE_NAME.isBlank()) {
			throw new IllegalArgumentException("O nome da fila não pode ser vazio");
		}
		QUEUE_NAME = qUEUE_NAME;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		if (user == null) {
			throw new IllegalArgumentException("O usuário não pode ser nulo!");
		}
		this.user = user;
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

	public MensagemIndividualController getMensagemIndividualController() {
		return mensagemIndividualController;
	}

	public void setMensagemIndividualController(MensagemIndividualController mensagemIndividualController) {
		this.mensagemIndividualController = mensagemIndividualController;
	}

	public String getResposta() {
		return resposta;
	}

	public User getContato() {
		return contato;
	}

	public void setContato(User contato) {
		if (contato == null) {
			throw new IllegalArgumentException("O contato não pode ser vazio");
		}
		this.contato = contato;
	}

	public PrincipalController getPrincipalController() {
		return principalController;
	}

	public void setPrincipalController(PrincipalController principalController) {
		this.principalController = principalController;
	}

}

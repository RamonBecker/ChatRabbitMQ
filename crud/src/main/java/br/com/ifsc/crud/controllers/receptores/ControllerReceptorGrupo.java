package br.com.ifsc.crud.controllers.receptores;

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

import br.com.ifsc.crud.controllersViews.MensagemGrupoController;
import br.com.ifsc.crud.entities.User;
import br.com.ifsc.crud.utility.MessageAlert;

public class ControllerReceptorGrupo {
	private String EXCHANGE_NAME;
	private static User user;
	public final static String HOST = "localhost";
	private final static String VHOST = "/";
	private Connection connection;
	private Channel channel;
	private static MensagemGrupoController mensagemGrupoController;
	private boolean ativo;


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

					String[] mensagemSeparada = message.split(";");
					System.out.println(mensagemSeparada[0]);
					System.out.println(mensagemSeparada[1]);
					
					if (getMensagemGrupoController().getTxtAreaMensagem().getText().isBlank()) {
						getMensagemGrupoController().getTxtAreaMensagem().setText(

								mensagemSeparada[0] + " enviou:" + mensagemSeparada[1]);
					} else {

						getMensagemGrupoController().getTxtAreaMensagem()
								.setText(getMensagemGrupoController().getTxtAreaMensagem().getText() + "\n"
										+ mensagemSeparada[0]  + " respondeu:" + mensagemSeparada[1]);

					}
				}
			};
			channel.basicConsume(queueName, true, consumer);
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
		ControllerReceptorGrupo.user = user;
	}

	public static User getUser() {
		return user;
	}

	public static MensagemGrupoController getMensagemGrupoController() {
		return mensagemGrupoController;
	}

	public void setMensagemGrupoController(MensagemGrupoController mensagemGrupoController) {
		ControllerReceptorGrupo.mensagemGrupoController = mensagemGrupoController;

	}

}

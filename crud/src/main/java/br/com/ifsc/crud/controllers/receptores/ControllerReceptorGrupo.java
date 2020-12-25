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

import br.com.ifsc.crud.controllers.ControllerUser;
import br.com.ifsc.crud.controllersViews.MensagemGrupoController;
import br.com.ifsc.crud.entities.Grupo;
import br.com.ifsc.crud.entities.User;
import br.com.ifsc.crud.utility.MessageAlert;
import javafx.application.Platform;

public class ControllerReceptorGrupo {
	private String EXCHANGE_NAME;
	private static User user;
	public final static String HOST = "localhost";
	private final static String VHOST = "/";
	private Connection connection;
	private Channel channel;
	private static MensagemGrupoController mensagemGrupoController;
	private boolean ativo;
	private String resposta;
	private Grupo grupo;
	private ControllerUser controllerUser;
	private User userLogado;

	public ControllerReceptorGrupo() {
		controllerUser = ControllerUser.getInstance();
		userLogado = controllerUser.getListUser().get(controllerUser.getUserLogado());
	}

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

					resposta = mensagemSeparada[0] + " enviou " + mensagemSeparada[1];

					if (getMensagemGrupoController() != null) {
						if (getMensagemGrupoController().getTxtAreaMensagem().getText().isBlank()) {
							getMensagemGrupoController().getTxtAreaMensagem().setText(resposta);

						} else {
							getMensagemGrupoController().getTxtAreaMensagem()
									.setText(getMensagemGrupoController().getTxtAreaMensagem().getText() + "\n"
											+ resposta);
						}
					}

					if (grupo.getMensagem() != null) {
						grupo.setMensagem(grupo.getMensagem() + "\n" + resposta);
					} else {
						grupo.setMensagem(resposta);
					}
					
					userLogado.getListGrupos().put(grupo.getName(), grupo);
					controllerUser.getListUser().put(userLogado.getUsername(), userLogado);

					Platform.runLater(() -> {
						MessageAlert.mensagemRealizadoSucesso("Grupo:" + grupo.getName() + "\n" + resposta);

					});
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

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
}

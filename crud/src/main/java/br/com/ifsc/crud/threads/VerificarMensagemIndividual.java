package br.com.ifsc.crud.threads;

import java.util.Iterator;

import br.com.ifsc.crud.controllers.ControllerUser;
import br.com.ifsc.crud.controllers.receptores.ControllerReceptorIndividual;
import br.com.ifsc.crud.entities.User;
import br.com.ifsc.crud.utility.MessageAlert;
import javafx.application.Platform;

public class VerificarMensagemIndividual extends Thread {

	private ControllerReceptorIndividual controllerReceptorIndividual;
	private ControllerUser controllerUser;
	private User userlogado;
	private String nameUserLogado;

	public VerificarMensagemIndividual() {
		this.controllerUser = ControllerUser.getInstance();
	}

	public void run() {

		verificarMensagemIndividual();

	}

	private void verificarMensagemIndividual() {
		while (true) {
			userlogado = controllerUser.getListUser().get(nameUserLogado);
			System.out.println(userlogado);
			for (String fila : userlogado.getControllerReceptorIndividual().keySet()) {
				ControllerReceptorIndividual receptor = userlogado.getControllerReceptorIndividual().get(fila);
				if (receptor.getResposta() != null) {
					if (!receptor.getResposta().isBlank()) {
						Platform.runLater(() -> {
							MessageAlert.mensagemRealizadoSucesso(
									"Usuário:" + userlogado.getUsername() + "\n" + receptor.getResposta());

						});
						//interrupt();
						return;
					}
				}
			}

		}
	}

	public User getUserlogado() {
		return userlogado;
	}

	public void setUserlogado(User userlogado) {
		this.userlogado = userlogado;
	}

	public String getNameUserLogado() {
		return nameUserLogado;
	}

	public void setNameUserLogado(String nameUserLogado) {
		this.nameUserLogado = nameUserLogado;
	}

}

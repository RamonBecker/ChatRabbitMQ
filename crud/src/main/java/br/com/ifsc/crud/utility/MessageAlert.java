package br.com.ifsc.crud.utility;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class MessageAlert {
	
	public static void mensagemRealizadoSucesso(String mensagem) {
		Alert mensagemAlert = new Alert(AlertType.INFORMATION);
		mensagemAlert.setHeaderText(mensagem);
		mensagemAlert.show();
	}
	
	public static void mensagemErro(String mensagem) {
		Alert mensagemErro = new Alert(AlertType.ERROR);
		mensagemErro.setTitle("Erro");
		mensagemErro.setHeaderText(mensagem);
		mensagemErro.show();
		
	}
}

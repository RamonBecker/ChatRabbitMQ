package br.com.ifsc.crud.controllersViews;

import java.net.URL;
import java.util.ResourceBundle;
import br.com.ifsc.crud.controllers.ControllerEmissorIndividual;
import br.com.ifsc.crud.controllers.ControllerReceptorIndividual;
import br.com.ifsc.crud.controllers.ControllerUser;
import br.com.ifsc.crud.entities.User1;
import br.com.ifsc.crud.utility.MessageAlert;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class MensagemIndividualController implements Initializable {
	@FXML
	private TextArea txtAreaMensagem;

	@FXML
	private TextField textMensagemUsuario;

	@FXML
	private Button btnEnviar;

	@FXML
	private Button btnSair;

	@FXML
	private TextField textContato;

	private ControllerEmissorIndividual controllerEmissorIndividual;

	private ControllerReceptorIndividual controllerReceptorIndividual;

	public static User1 contato;

	private User1 userLogado;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		

		userLogado = ControllerUser.getInstance().getListUser().get(ControllerUser.getInstance().getUserLogado());
		
		ControllerEmissorIndividual.setUser(userLogado);
		controllerEmissorIndividual = new ControllerEmissorIndividual();
		ControllerEmissorIndividual.setQUEUE_NAME(contato.getUsername() + "" + userLogado.getUsername());

		controllerReceptorIndividual = new ControllerReceptorIndividual();
		ControllerReceptorIndividual.setQUEUE_NAME(userLogado.getUsername() + "" + contato.getUsername());
		ControllerReceptorIndividual.setUser(userLogado);

		
		
		
		textContato.setText(contato.getUsername());
		
		
		System.out.println("Nome da fila emissor:"+ControllerEmissorIndividual.getQUEUE_NAME()+" tamanho:"+ControllerEmissorIndividual.getQUEUE_NAME().length());
		System.out.println("Nome da file receptor:"+ControllerReceptorIndividual.getQUEUE_NAME()+" tamanho:"+ControllerReceptorIndividual.getQUEUE_NAME().length());
		
		
		String [] mes = new String[1];
		mes[0] = "";
		ControllerReceptorIndividual.main(mes);

	}

	public void enviarMensagem() {
		System.out.println("Usuaário logado:"+userLogado);
		System.out.println("contato:"+contato); 
		try {

			controllerEmissorIndividual.setMensagem(textMensagemUsuario.getText().trim());
			if (txtAreaMensagem.getText().isBlank()) {
				txtAreaMensagem.setText(userLogado.getUsername() + " enviou a mensagem: "
						+ textMensagemUsuario.getText().trim() + " para o contato: " + contato.getUsername());

			
			} else {
				txtAreaMensagem
						.setText(txtAreaMensagem.getText() + "\n" + userLogado.getUsername() + " enviou a mensagem: "
								+ textMensagemUsuario.getText().trim() + " para o contato: " + contato.getUsername());
			
				txtAreaMensagem.setText("\nUsuário:"+userLogado.getUsername()+" recebeu a mensagem:"+ControllerReceptorIndividual.getMensagem()+"\n");
			}

			textMensagemUsuario.setText("");
			controllerEmissorIndividual.enviarMensagem();
			
		
			System.out.println("Usuário:"+userLogado.getUsername()+" recebeu a mensagem:"+ControllerReceptorIndividual.getMensagem()+"");
			
			
			
		//	controllerReceptorIndividual.run();

			//ControllerReceptorIndividual.sleep(1000);
		} catch (Exception e) {
			MessageAlert.mensagemErro(e.getMessage());
		}

	}

	public void sairConversa() {

	}

}

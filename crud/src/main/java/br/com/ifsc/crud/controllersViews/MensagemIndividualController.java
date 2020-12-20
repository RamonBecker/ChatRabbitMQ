package br.com.ifsc.crud.controllersViews;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import br.com.ifsc.crud.App1;
import br.com.ifsc.crud.controllers.ControllerUser;
import br.com.ifsc.crud.controllers.emissores.ControllerEmissorIndividual;
import br.com.ifsc.crud.controllers.receptores.ControllerReceptorIndividual;
import br.com.ifsc.crud.entities.User;
import br.com.ifsc.crud.utility.MessageAlert;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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

	@FXML
	private TextField textUsuario;

	private ControllerEmissorIndividual controllerEmissorIndividual;

	private ControllerReceptorIndividual controllerReceptorIndividual;

	public static User contato;

	private User userLogado;

	private ControllerUser controllerUser;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		controllerUser = ControllerUser.getInstance();
		userLogado = ControllerUser.getInstance().getListUser().get(ControllerUser.getInstance().getUserLogado());

		ControllerEmissorIndividual.setUser(userLogado);
		controllerEmissorIndividual = new ControllerEmissorIndividual();
		ControllerEmissorIndividual.setQUEUE_NAME(contato.getUsername() + "" + userLogado.getUsername());

		controllerReceptorIndividual = new ControllerReceptorIndividual();
		ControllerReceptorIndividual.setQUEUE_NAME(userLogado.getUsername() + "" + contato.getUsername());
		ControllerReceptorIndividual.setUser(userLogado);
		ControllerReceptorIndividual.setContato(contato);

		textContato.setText(contato.getUsername());
		textUsuario.setText(userLogado.getUsername());

		controllerEmissorIndividual.setMensagemIndividualController(this);
		ControllerReceptorIndividual.setMensagemIndividualController(this);

		iniciarReceptor();
		verificarConversaInserida();
	}

	private void iniciarReceptor() {
		controllerReceptorIndividual.iniciarReceptorIndividual();
	}

	public void enviarMensagem() {
		try {

			controllerEmissorIndividual.setMensagem(textMensagemUsuario.getText().trim());
			if (txtAreaMensagem.getText().isBlank()) {
				txtAreaMensagem.setText(userLogado.getUsername() + " enviou: " + textMensagemUsuario.getText().trim());

			} else {
				txtAreaMensagem.setText(txtAreaMensagem.getText() + "\n" + userLogado.getUsername()
						+ " enviou a mensagem: " + textMensagemUsuario.getText().trim());
			}

			textMensagemUsuario.setText("");
			controllerEmissorIndividual.enviarMensagem();

		} catch (Exception e) {
			MessageAlert.mensagemErro(e.getMessage());
		}

	}

	private void verificarConversaInserida() {

		if (userLogado.getFilaMensagemIndividual().containsKey(ControllerEmissorIndividual.getQUEUE_NAME())) {

			if (!userLogado.getFilaMensagemIndividual().get(ControllerEmissorIndividual.getQUEUE_NAME()).isEmpty()) {
				txtAreaMensagem.setText(
						userLogado.getFilaMensagemIndividual().get(ControllerEmissorIndividual.getQUEUE_NAME()));
			}
		}

	}

	public void sairConversa() {
		userLogado.getFilaMensagemIndividual().put(ControllerEmissorIndividual.getQUEUE_NAME(),
				txtAreaMensagem.getText());
		contato.getFilaMensagemIndividual().put(ControllerReceptorIndividual.getQUEUE_NAME(),
				txtAreaMensagem.getText());
		userLogado.getListContatos().put(contato.getUsername(), contato);
		contato.getListContatos().put(userLogado.getUsername(), userLogado);

		controllerUser.getListUser().put(userLogado.getUsername(), userLogado);
		controllerUser.getListUser().put(contato.getUsername(), contato);
		
		controllerReceptorIndividual.fecharConexao();

		try {
			FXMLLoader fxmlLoader = new FXMLLoader(App1.class.getResource("principal.fxml"));

			Parent root;
			root = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.setScene(new Scene(root));
			stage.show();

			Scene scene = btnEnviar.getScene();
			Stage stageWindow = (Stage) scene.getWindow();
			stageWindow.close();
		} catch (IOException e) {
			MessageAlert.mensagemErro("Não foi possível sair da conversa");
			e.printStackTrace();
		}

	}

	public TextArea getTxtAreaMensagem() {
		return txtAreaMensagem;
	}

}

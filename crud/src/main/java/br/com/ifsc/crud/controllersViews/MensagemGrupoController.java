package br.com.ifsc.crud.controllersViews;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import br.com.ifsc.crud.controllers.ControllerUser;
import br.com.ifsc.crud.controllers.emissores.ControllerEmissorGrupo;
import br.com.ifsc.crud.controllers.receptores.ControllerReceptorGrupo;
import br.com.ifsc.crud.entities.Grupo;
import br.com.ifsc.crud.entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class MensagemGrupoController implements Initializable {

	@FXML
	private TextArea txtAreaMensagem;

	@FXML
	private TextField textMensagemUsuario;

	@FXML
	private Button btnEnviar;

	@FXML
	private Button btnSair;

	@FXML
	private TextField textUsuario;

	@FXML
	private ListView<User> listViewContatos;

	public static Grupo grupo;

	private User userLogado;

	private ControllerUser controllerUser;

	private ObservableList<User> listContatos = FXCollections.observableArrayList();

	private ControllerReceptorGrupo controllerReceptorGrupo;

	private ControllerEmissorGrupo controllerEmissorGrupo;

	private Map<String, ControllerReceptorGrupo> listReceptorGrupo;

	private Map<String, ControllerEmissorGrupo> listEmissorGrupo;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		listReceptorGrupo = new HashMap<String, ControllerReceptorGrupo>();
		listEmissorGrupo = new HashMap<String, ControllerEmissorGrupo>();

		controllerUser = ControllerUser.getInstance();
		userLogado = controllerUser.getListUser().get(controllerUser.getUserLogado());
		textUsuario.setText(userLogado.getUsername());
		preencherListViewContato();
		preencherListasEmissorReceptor();
		iniciarReceptores();

	}

	private void preencherListasEmissorReceptor() {

		for (User contato : listContatos) {
			controllerReceptorGrupo = new ControllerReceptorGrupo();
			controllerReceptorGrupo.setNomeFila(userLogado.getUsername() + "" + contato.getUsername());
			controllerReceptorGrupo.setUser(userLogado);
			controllerReceptorGrupo.setMensagemGrupoController(this);

			controllerEmissorGrupo = new ControllerEmissorGrupo();
			controllerEmissorGrupo.setNomeFila(contato.getUsername() + "" + userLogado.getUsername());
			controllerEmissorGrupo.setUser(userLogado);

			listReceptorGrupo.put(controllerReceptorGrupo.getEXCHANGE_NAME(), controllerReceptorGrupo);
			listEmissorGrupo.put(controllerEmissorGrupo.getEXCHANGE_NAME(), controllerEmissorGrupo);

		}
	}

	private void preencherListContatosObservable() {
		for (String username : userLogado.getListContatos().keySet()) {
			User user = controllerUser.getListUser().get(username);
			listContatos.add(user);
		}
	}

	private void preencherListViewContato() {
		preencherListContatosObservable();
		listViewContatos.getItems().clear();
		listViewContatos.setItems(listContatos);
	}

	private void iniciarReceptores() {
		for (String filaReceptor : listReceptorGrupo.keySet()) {
			ControllerReceptorGrupo grupo = listReceptorGrupo.get(filaReceptor);
			System.out.println("Receptor fila:" + grupo.getEXCHANGE_NAME());
			grupo.iniciarReceptorGrupo();
		}

	}

	public void enviarMensagem() {

		if (txtAreaMensagem.getText().isBlank()) {
			txtAreaMensagem.setText(userLogado.getUsername() + " enviou: " + textMensagemUsuario.getText().trim());

		} else {
			txtAreaMensagem.setText(txtAreaMensagem.getText() + "\n" + userLogado.getUsername() + " enviou a mensagem: "
					+ textMensagemUsuario.getText().trim());
		}

		for (String fila : listEmissorGrupo.keySet()) {
			ControllerEmissorGrupo emissor = listEmissorGrupo.get(fila);
			emissor.setMensagem(userLogado.getUsername() + ";" + textMensagemUsuario.getText().trim());
			emissor.enviarMensagem();

		}
		textMensagemUsuario.setText("");

	}

	public void sair() {

	}

	public TextArea getTxtAreaMensagem() {
		return txtAreaMensagem;
	}

}

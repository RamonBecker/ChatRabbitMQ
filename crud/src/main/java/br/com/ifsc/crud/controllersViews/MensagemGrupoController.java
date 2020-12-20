package br.com.ifsc.crud.controllersViews;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import br.com.ifsc.crud.App1;
import br.com.ifsc.crud.controllers.ControllerUser;
import br.com.ifsc.crud.controllers.emissores.ControllerEmissorGrupo;
import br.com.ifsc.crud.controllers.receptores.ControllerReceptorGrupo;
import br.com.ifsc.crud.entities.Grupo;
import br.com.ifsc.crud.entities.User;
import br.com.ifsc.crud.utility.MessageAlert;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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

	@FXML
	private TextField textGrupo;

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

		textGrupo.setText(grupo.getName());
		listReceptorGrupo = new HashMap<String, ControllerReceptorGrupo>();
		listEmissorGrupo = new HashMap<String, ControllerEmissorGrupo>();

		controllerUser = ControllerUser.getInstance();
		userLogado = controllerUser.getListUser().get(controllerUser.getUserLogado());
		textUsuario.setText(userLogado.getUsername());
		preencherListViewContato();
		preencherListasEmissorReceptor();
		verificarConversaInserida();
		iniciarReceptores();
		// verificarConversaInserida();

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
		for (String username : grupo.getListUsers().keySet()) {
			User user = grupo.getListUsers().get(username);
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

	private void verificarConversaInserida() {
		if (userLogado.getListGrupos().containsKey(grupo.getName())) {
			Grupo aux_grupo = userLogado.getListGrupos().get(grupo.getName());
			System.out.println("Nome do grupo:" + aux_grupo.getName());
			if (aux_grupo.getMensagem() != null && !(aux_grupo.getMensagem().isBlank())) {
				txtAreaMensagem.setText(aux_grupo.getMensagem());
			}
		}
	}

	public void sair() {
		grupo.setMensagem(txtAreaMensagem.getText());

		for (String username : grupo.getListUsers().keySet()) {
			if (controllerUser.getListUser().containsKey(username)) {
				User contato = controllerUser.getListUser().get(username);
				Grupo grupoContato = contato.getListGrupos().get(grupo.getName());
				grupoContato.setMensagem(txtAreaMensagem.getText());
				contato.getListGrupos().put(grupoContato.getName(), grupo);
				grupo.getListUsers().put(contato.getUsername(), contato);
			}
		}
		userLogado.getListGrupos().put(grupo.getName(), grupo);

		for (String nameFilaReceptor : listReceptorGrupo.keySet()) {
			ControllerReceptorGrupo controllerReceptorGrupo = listReceptorGrupo.get(nameFilaReceptor);
			controllerReceptorGrupo.fecharConexao();
		}

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

package br.com.ifsc.crud.controllersViews;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import br.com.ifsc.crud.App1;
import br.com.ifsc.crud.controllers.ControllerUser;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class RegisterGrupoController implements Initializable {

	@FXML
	private TextField textNomeGrupo;

	@FXML
	private ListView<User> listViewContatos;

	@FXML
	private Button btnAdicionar;
	@FXML
	private TableView<User> tableViewContato;

	@FXML
	private TableColumn<User, String> tableColumnContato;

	@FXML
	private Button btnVoltar;

	@FXML
	private RadioButton radioSim;

	@FXML
	private RadioButton radioNao;

	@FXML
	private ToggleGroup group1;

	private ObservableList<User> listContatos = FXCollections.observableArrayList();

	private ControllerUser controllerUser;

	private User userLogado;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		controllerUser = ControllerUser.getInstance();
		preencherListViewContato();
	}

	private void preencherListContatosObservable() {
		userLogado = controllerUser.getListUser().get(controllerUser.getUserLogado());
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

	public void actionAddGrupo() {

		User aux_user = controllerUser.getListUser().get(controllerUser.getUserLogado());

		Grupo grupo = new Grupo();

		grupo.setName(textNomeGrupo.getText().trim());

		if (group1.getSelectedToggle() == null) {
			MessageAlert.mensagemErro("Selecione uma opção !");
			return;
		}

		if (radioSim.isSelected()) {
			int cont = 0;
			if (aux_user.getListGrupos().containsKey(textNomeGrupo.getText())) {
				Grupo aux_grupo = aux_user.getListGrupos().get(textNomeGrupo.getText());

				for (User contato : listContatos) {
					if (aux_grupo.getListUsers().containsKey(contato.getUsername())) {
						cont++;
					}
				}
				if (cont == aux_grupo.getListUsers().size()) {
					MessageAlert.mensagemErro("Todos estes usuários já estão inseridos neste grupo");
					cleanFields();
					return;
				}
			} else {

				for (User contato : listContatos) {
					grupo.getListUsers().put(contato.getUsername(), contato);
				}
				adicionarGrupo(aux_user, grupo);
			}
		}
		if (radioNao.isSelected()) {
			User auxContato = listViewContatos.getSelectionModel().getSelectedItem();
			System.out.println("AUX contato:" + auxContato);
			if (aux_user.getListGrupos().containsKey(textNomeGrupo.getText())) {
				System.out.println("Passou do primeiro IF");
				if (aux_user.getListGrupos().get(textNomeGrupo.getText()).getListUsers()
						.containsKey(auxContato.getUsername())) {
					MessageAlert.mensagemErro("Esse usuário já está adicionado no grupo!");
					cleanFields();
					return;
				} else {
					adicionarGrupo(aux_user, grupo, auxContato);
				}
			} else {
				adicionarGrupo(aux_user, grupo, auxContato);
			}
		}
	}

	private void adicionarGrupo(User user, Grupo grupo) {
		user.getListGrupos().put(textNomeGrupo.getText(), grupo);
		controllerUser.getListUser().put(user.getUsername(), user);

		for (String username : grupo.getListUsers().keySet()) {
			if (controllerUser.getListUser().containsKey(username)) {
				User contato = controllerUser.getListUser().get(username);
				contato.getListGrupos().put(grupo.getName(), grupo);
				controllerUser.getListUser().put(contato.getUsername(), contato);
			}
		}

		MessageAlert.mensagemRealizadoSucesso("Contatos adicionados com sucesso!");
		listarGrupos();
		cleanFields();
	}

	private void adicionarGrupo(User user, Grupo grupo, User contato) {
		contato.getListGrupos().put(grupo.getName(), grupo);

		grupo.getListUsers().put(contato.getUsername(), contato);
		user.getListGrupos().put(textNomeGrupo.getText(), grupo);
		controllerUser.getListUser().put(user.getUsername(), user);
		controllerUser.getListUser().put(contato.getUsername(), contato);
		MessageAlert.mensagemRealizadoSucesso("Contato adicionado com sucesso!");
		listarGrupos();
		cleanFields();
	}

	private void cleanFields() {
		textNomeGrupo.setText("");
		listViewContatos.setDisable(true);
		radioNao.setSelected(false);
		radioSim.setSelected(false);
	}

	private void listarGrupos() {
		User aux_user = controllerUser.getListUser().get(controllerUser.getUserLogado());
		System.out.println("----------------");
		for (String nomeGrupo : aux_user.getListGrupos().keySet()) {
			Grupo grupo = aux_user.getListGrupos().get(nomeGrupo);
			System.out.println("Nome do grupo:" + grupo);
			for (String nomeUser : grupo.getListUsers().keySet()) {
				User contato = grupo.getListUsers().get(nomeUser);
				System.out.println("Usuário do grupo:" + contato.getUsername());
			}
		}
		System.out.println("----------------");

	}

	public void voltar() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(App1.class.getResource("principal.fxml"));

			Parent root;

			root = (Parent) fxmlLoader.load();

			Stage stage = new Stage();
			stage.setScene(new Scene(root));
			stage.show();

			Scene scene = btnVoltar.getScene();
			Stage stageWindow = (Stage) scene.getWindow();
			stageWindow.close();

		} catch (IOException e) {
			MessageAlert.mensagemErro("Não foi possível voltar para a tela principal!");
			e.printStackTrace();
		}
	}

	public void actionRadioButton() {
		if (radioSim.isSelected()) {
			listViewContatos.setDisable(true);
		}
		if (radioNao.isSelected()) {
			listViewContatos.setDisable(false);
		}
	}

}
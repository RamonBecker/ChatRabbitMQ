package br.com.ifsc.crud.controllersViews;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import br.com.ifsc.crud.App;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PrincipalController implements Initializable {

	@FXML
	private Button btnGrupo;

	@FXML
	private ListView<Grupo> listViewGrupo;

	@FXML
	private ListView<User> listViewContatos;

	@FXML
	private Button btnSair;

	@FXML
	private TextField textUsuario;

	private ControllerUser controllerUser = ControllerUser.getInstance();

	private ObservableList<User> listContatos = FXCollections.observableArrayList();

	private ObservableList<Grupo> listGrupos = FXCollections.observableArrayList();

	private User userContato;

	private User userLogado;
	
	private Grupo grupo;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		userLogado = controllerUser.getListUser().get(controllerUser.getUserLogado());
		textUsuario.setText(userLogado.getUsername());
		preencherListViewGrupo();
		preencherListViewContato();
	}

	private void preencherListContatosObservable() {
		for (String username : userLogado.getListContatos().keySet()) {
			User user = controllerUser.getListUser().get(username);
			listContatos.add(user);
		}
	}

	private void preencherListGrupoObservable() {
		for (String nomeGrupo : userLogado.getListGrupos().keySet()) {
			Grupo grupo = userLogado.getListGrupos().get(nomeGrupo);
			listGrupos.add(grupo);
		}
	}

	private void preencherListViewContato() {
		preencherListContatosObservable();
		listViewContatos.getItems().clear();
		listViewContatos.setItems(listContatos);

	}

	private void preencherListViewGrupo() {
		preencherListGrupoObservable();
		listViewGrupo.getItems().clear();
		listViewGrupo.setItems(listGrupos);
	}

	public void actionListViewContato() {
		userContato = listViewContatos.getSelectionModel().getSelectedItem();

		try {

			Scene scene = btnGrupo.getScene();
			Stage stageWindow = (Stage) scene.getWindow();
			stageWindow.close();

			MensagemIndividualController.contato = userContato;
			Stage stage = new Stage();
			FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("mensagemIndividual.fxml"));
			Parent root;
			root = (Parent) fxmlLoader.load();
			stage.setScene(new Scene(root));
			stage.show();

		} catch (IOException e) {
			MessageAlert.mensagemErro("Ocorreu um erro ao iniciar uma conversa!");
			e.printStackTrace();
		}

	}

	public void actionListViewGrupo() {
		grupo = listViewGrupo.getSelectionModel().getSelectedItem();
		try {
		Scene scene = btnGrupo.getScene();
		Stage stageWindow = (Stage) scene.getWindow();
		stageWindow.close();

		MensagemGrupoController.grupo = grupo;
		Stage stage = new Stage();
		FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("mensagemGrupo.fxml"));
		Parent root;
		
			root = (Parent) fxmlLoader.load();
		
		stage.setScene(new Scene(root));
		stage.show();
		
		} catch (IOException e) {
			MessageAlert.mensagemErro("Ocorreu um erro ao iniciar uma conversa em grupo!");
			e.printStackTrace();
		}
		
	}
	
	public void actionAddGrupo() {
		try {
			Scene scene = btnGrupo.getScene();
			Stage stageWindow = (Stage) scene.getWindow();
			stageWindow.close();

			Stage stage = new Stage();
			FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("registerGrupo.fxml"));
			Parent root;
			root = (Parent) fxmlLoader.load();
			stage.setScene(new Scene(root));
			stage.show();

		} catch (IOException e) {
			MessageAlert.mensagemErro("Ocorreu um erro ao abrir a tela para adicionar um grupo!");
			e.printStackTrace();
		}
	}

	public void sair() {
		try {
			Scene scene = btnGrupo.getScene();
			Stage stageWindow = (Stage) scene.getWindow();
			stageWindow.close();

			Stage stage = new Stage();
			FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("login.fxml"));
			Parent root;

			root = (Parent) fxmlLoader.load();
			stage.setScene(new Scene(root));
			stage.show();
		} catch (IOException e) {
			MessageAlert.mensagemErro("Ocorreu um erro ao sair!");

			e.printStackTrace();
		}

	}
}

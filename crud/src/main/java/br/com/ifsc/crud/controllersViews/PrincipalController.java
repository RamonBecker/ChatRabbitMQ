package br.com.ifsc.crud.controllersViews;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import br.com.ifsc.crud.App;
import br.com.ifsc.crud.App1;
import br.com.ifsc.crud.controllers.ControllerUser;
import br.com.ifsc.crud.entities.User1;
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
import javafx.stage.Stage;

public class PrincipalController implements Initializable {

	@FXML
	private Button btnGrupo;

	@FXML
	private ListView<User1> listViewGrupo;

	@FXML
	private ListView<User1> listViewContatos;

	private ControllerUser controllerUser = ControllerUser.getInstance();

	private ObservableList<User1> listContatos = FXCollections.observableArrayList();

	private User1 userContato;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		preencherListViewContato();
	}

	private void preencherListContatosObservable() {
		User1 userLogado = controllerUser.getListUser().get(controllerUser.getUserLogado());
		for (String username : userLogado.getListContatos().keySet()) {
			User1 user = controllerUser.getListUser().get(username);
			listContatos.add(user);
		}
	}

	private void preencherListViewContato() {
		preencherListContatosObservable();
		listViewContatos.getItems().clear();
		listViewContatos.setItems(listContatos);

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

}

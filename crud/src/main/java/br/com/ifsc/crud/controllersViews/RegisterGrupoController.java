package br.com.ifsc.crud.controllersViews;

import java.net.URL;
import java.util.ResourceBundle;

import br.com.ifsc.crud.controllers.ControllerUser;
import br.com.ifsc.crud.entities.User1;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class RegisterGrupoController implements Initializable {

	@FXML
	private TextField textNomeGrupo;

	@FXML
	private ListView<User1> listViewContatos;

	@FXML
	private Button btnAdicionar;
	@FXML
	private TableView<User1> tableViewContato;

	@FXML
	private TableColumn<User1, String> tableColumnContato;

	private ObservableList<User1> listContatos = FXCollections.observableArrayList();

	private ControllerUser controllerUser = ControllerUser.getInstance();
	
	private User1 userLogado;

	private void preencherListContatosObservable() {
		userLogado = controllerUser.getListUser().get(controllerUser.getUserLogado());
		for (String username : userLogado.getListContatos().keySet()) {
			User1 user = controllerUser.getListUser().get(username);
			listContatos.add(user);
		}
	}

	private void preencherListViewContato() {
		preencherListContatosObservable();
		listViewContatos.getItems().clear();
		listViewContatos.setItems(listContatos);
		System.out.println(listContatos.size());

	}

	public void actionAddGrupo() {
		

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	
		preencherListViewContato();
	}

//	@FXML
//	private void register(ActionEvent e) {
////		User user = new User(txtName.getText(), Integer.valueOf(txtAge.getText()), "");
////		new UserDAO().add(user);
////		listController.updateList();
////		Button btn = (Button) e.getSource();
////		Scene scene = btn.getScene();
////		Stage stage = (Stage) scene.getWindow();
////		stage.close();
//	}

}
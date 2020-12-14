package br.com.ifsc.crud.controllersViews;


import br.com.ifsc.crud.entities.User1;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegisterGrupoController {

    @FXML
    private TextField textNomeGrupo;

    @FXML
    private ListView<User1> listViewContatos;

    @FXML
    private Button btnAdicionar;
	

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
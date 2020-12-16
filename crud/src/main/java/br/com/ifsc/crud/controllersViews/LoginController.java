package br.com.ifsc.crud.controllersViews;

import br.com.ifsc.crud.App1;
import br.com.ifsc.crud.controllers.ControllerUser;
import br.com.ifsc.crud.utility.MessageAlert;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

	@FXML
	private TextField textUsername;

	@FXML
	private PasswordField textPassword;

	@FXML
	private Button btnLogin;

	private ControllerUser controllerUser;
	

	@FXML
	public void login(ActionEvent e) {
		controllerUser = ControllerUser.getInstance();
		System.out.println(textUsername.getText().trim());
		System.out.println(textPassword.getText().trim());
		try {
			controllerUser.login(textUsername.getText().trim(), textPassword.getText().trim());
			controllerUser.setUserLogado(textUsername.getText());
			
			FXMLLoader fxmlLoader = new FXMLLoader(App1.class.getResource("principal.fxml"));
			
			Parent root = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.setScene(new Scene(root));
			stage.show();


			Scene scene = btnLogin.getScene();
			Stage stageWindow = (Stage) scene.getWindow();
			stageWindow.close();

		} catch (Exception exception) {
			MessageAlert.mensagemErro(exception.getMessage());
		}
	}

}

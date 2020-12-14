package br.com.ifsc.crud;

import java.io.IOException;

import br.com.ifsc.crud.controllersViews.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

	private static Scene scene;
	public static String login = "Login";

	@Override
	public void start(Stage stage) throws IOException, InterruptedException {

		if (login.equals("Login")) {
			scene = new Scene(loadFXML("login"));
			stage.setScene(scene);
			stage.show();
		}
	}

	public static void setRoot(String fxml) throws IOException {
		scene.setRoot(loadFXML(fxml));
	}

	private static Parent loadFXML(String fxml) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
		return fxmlLoader.load();
	}

}
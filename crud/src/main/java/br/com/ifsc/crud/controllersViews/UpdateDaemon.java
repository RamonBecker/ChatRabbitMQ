package br.com.ifsc.crud.controllersViews;

import java.util.List;

import br.com.ifsc.crud.dao.UserDAO;
import br.com.ifsc.crud.entities.User;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

public class UpdateDaemon implements Runnable {
	private ListView<User> listViewUsers;

	public UpdateDaemon(ListView<User> listViewUsers) {
		this.listViewUsers = listViewUsers;
	}

	@Override
	public void run() {
		while (true) {
			try {
				List<User> users = new UserDAO().getAll();
				Platform.runLater(() -> {
					listViewUsers.setItems(null);
					listViewUsers.setItems((ObservableList<User>) users);
				});
				System.out.println("O daemon atualizou os usuários");
				Thread.sleep(5000);
			} catch (Exception e) {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e1) {
				}
			}
		}
	}

}

package br.com.ifsc.crud.controllers;

import java.util.HashMap;
import java.util.Map;

import br.com.ifsc.crud.entities.Grupo;
import br.com.ifsc.crud.entities.User;

public class ControllerUser {
	private Map<String, User> listUser;
	private static ControllerUser controllerUser;
	private String userLogado;

	private ControllerUser() {
		this.listUser = new HashMap<String, User>();
		User user1 = new User("becker", "becker");
		User user2 = new User("silva", "silva");
		User user3 = new User("ramon", "ramon");

		user1.getListContatos().put(user2.getUsername(), user2);
		user1.getListContatos().put(user3.getUsername(), user3);

		user2.getListContatos().put(user1.getUsername(), user1);
		user2.getListContatos().put(user3.getUsername(), user3);

		user3.getListContatos().put(user1.getUsername(), user1);
		user3.getListContatos().put(user2.getUsername(), user2);

		this.listUser.put(user1.getUsername(), user1);
		this.listUser.put(user2.getUsername(), user2);
		this.listUser.put(user3.getUsername(), user3);
	}

	public static ControllerUser getInstance() {
		if (controllerUser == null) {
			controllerUser = new ControllerUser();
		}
		return controllerUser;
	}

	public void login(String username, String password) throws Exception {
		if (getListUser().containsKey(username)) {
			User user = getListUser().get(username);
			System.out.println("USER:" + user);
			if (!user.getPassword().equals(password)) {
				throw new Exception("Usuário ou/e senha incorretos !");
			}
		} else {
			throw new Exception("Usuário ou/e senha incorretos !");
		}
	}

	public String getUserLogado() {
		return userLogado;
	}

	public void setUserLogado(String userLogado) {
		if (userLogado == null || userLogado.isBlank()) {
			throw new IllegalArgumentException("O usuário logado não pode ser vazio");
		}
		this.userLogado = userLogado;
	}
	
	public void addGrupo(String grupo, User userLogado, User contato) {
		
		
//		if(userLogado.getListGrupos().containsKey(grupo)) {
//			Grupo grupoAtual = userLogado.getListGrupos().get(grupo);
//			grupoAtual.getListUsers().put(contato.getUsername(), contato);
//			userLogado.getListGrupos().put(grupoAtual.getName(), grupoAtual);
//			listUser.put(userLogado.getUsername(), user)
//		}
//		
		//Grupo grupoNovo = new Grupo(grupo, listUsers);
		//userLogado.getListGrupos().put(key, value)
	}

	public Map<String, User> getListUser() {
		if (this.listUser == null) {
			this.listUser = new HashMap<String, User>();
		}
		return listUser;
	}

}

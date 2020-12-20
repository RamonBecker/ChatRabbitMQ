package br.com.ifsc.crud.entities;

import java.util.HashMap;
import java.util.Map;

public class User {

	private String username;
	private String password;
	private Map<String, Grupo> listGrupos;
	private Map<String, User> listContatos;
	private Map<String, String> filaIndividual;

	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		if (username == null || username.isBlank()) {
			throw new IllegalArgumentException("O username não pode ser vazio");
		}
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		if (password == null || password.isBlank()) {
			throw new IllegalArgumentException("A password não pode ser vazio");
		}
		this.password = password;
	}

	public Map<String, Grupo> getListGrupos() {
		if (this.listGrupos == null) {
			this.listGrupos = new HashMap<String, Grupo>();
		}
		return listGrupos;
	}

	public void setListGrupos(Map<String, Grupo> listGrupos) {
		this.listGrupos = listGrupos;
	}

	public Map<String, User> getListContatos() {
		if (this.listContatos == null) {
			this.listContatos = new HashMap<String, User>();
		}
		return listContatos;
	}

	public void setListContatos(Map<String, User> listContatos) {
		this.listContatos = listContatos;
	}

	public Map<String, String> getFilaIndividual() {
		if (filaIndividual == null) {
			filaIndividual = new HashMap<String, String>();
		}
		return filaIndividual;
	}

	public void setFilaIndividual(Map<String, String> filaIndividual) {
		this.filaIndividual = filaIndividual;
	}

	@Override
	public String toString() {
		return "Usuário:" + username;
	}

}

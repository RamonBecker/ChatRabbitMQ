package br.com.ifsc.crud.entities;

import java.util.HashMap;
import java.util.Map;

public class Grupo {
	private String name;
	private Map<String, User> listUsers;

	public Grupo() {
		this.listUsers = new HashMap<String, User>();
	}

	public Grupo(String name, Map<String, User> listUsers) {
		this.name = name;
		this.listUsers = listUsers;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, User> getListUsers() {
		if (this.listUsers == null) {
			this.listUsers = new HashMap<String, User>();
		}
		return listUsers;
	}

	public void setListUsers(Map<String, User> listUsers) {
		this.listUsers = listUsers;
	}

	@Override
	public String toString() {
		return "Grupo:" + name;
	}

}

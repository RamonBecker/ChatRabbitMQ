package br.com.ifsc.crud.entities;

import java.util.HashMap;
import java.util.Map;

public class Grupo {
	private String name;
	private Map<String, User1> listUsers;

	public Grupo() {
		this.listUsers = new HashMap<String, User1>();
	}

	public Grupo(String name, Map<String, User1> listUsers) {
		this.name = name;
		this.listUsers = listUsers;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, User1> getListUsers() {
		if (this.listUsers == null) {
			this.listUsers = new HashMap<String, User1>();
		}
		return listUsers;
	}

	public void setListUsers(Map<String, User1> listUsers) {
		this.listUsers = listUsers;
	}

	@Override
	public String toString() {
		return "Grupo [name=" + name + ", listUsers=" + listUsers + "]";
	}

}

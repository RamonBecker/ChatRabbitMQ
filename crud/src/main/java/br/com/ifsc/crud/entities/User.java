package br.com.ifsc.crud.entities;

import java.util.HashMap;
import java.util.Map;
import br.com.ifsc.crud.controllers.emissores.ControllerEmissorGrupo;
import br.com.ifsc.crud.controllers.receptores.ControllerReceptorGrupo;
import br.com.ifsc.crud.controllers.receptores.ControllerReceptorIndividual;

public class User {

	private String username;
	private String password;
	private Map<String, Grupo> listGrupos;
	private Map<String, User> listContatos;
	private Map<String, String> filaMensagemIndividual;
	private Map<String, String> filaMensagemGrupo;
	private Map<String, ControllerReceptorIndividual> controllerReceptorIndividual;
	private Map<String, ControllerReceptorGrupo> controllerReceptorGrupo;
	private Map<String, ControllerEmissorGrupo> controllerEmissorGrupo;

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

	public Map<String, User> getListContatos() {
		if (this.listContatos == null) {
			this.listContatos = new HashMap<String, User>();
		}
		return listContatos;
	}

	public Map<String, String> getFilaMensagemIndividual() {
		if (filaMensagemIndividual == null) {
			filaMensagemIndividual = new HashMap<String, String>();
		}
		return filaMensagemIndividual;
	}

	public Map<String, String> getFilaMensagemGrupo() {
		if (filaMensagemGrupo == null) {
			filaMensagemGrupo = new HashMap<String, String>();
		}
		return filaMensagemGrupo;
	}

	public Map<String, ControllerReceptorIndividual> getControllerReceptorIndividual() {
		if (controllerReceptorIndividual == null) {
			controllerReceptorIndividual = new HashMap<String, ControllerReceptorIndividual>();
		}
		return controllerReceptorIndividual;
	}

	public Map<String, ControllerReceptorGrupo> getControllerReceptorGrupo() {
		if (controllerReceptorGrupo == null) {
			controllerReceptorGrupo = new HashMap<String, ControllerReceptorGrupo>();
		}
		return controllerReceptorGrupo;
	}

	public Map<String, ControllerEmissorGrupo> getControllerEmissorGrupo() {
		if (controllerEmissorGrupo == null) {
			controllerEmissorGrupo = new HashMap<String, ControllerEmissorGrupo>();
		}
		return controllerEmissorGrupo;
	}

	@Override
	public String toString() {
		return "Usuário:" + username;
	}

}

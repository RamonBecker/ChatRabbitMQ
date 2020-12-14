package br.com.ifsc.crud.entities;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class User {

	private String name;

	private int age;

	private String registerDate;

	public User() {
	}

	public User(String name, int age, String registerDate) {
		super();
		this.name = name;
		this.age = age;
		this.registerDate = registerDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(String registerDate) {
		this.registerDate = registerDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + age;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((registerDate == null) ? 0 : registerDate.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (age != other.age)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (registerDate == null) {
			if (other.registerDate != null)
				return false;
		} else if (!registerDate.equals(other.registerDate))
			return false;
		return true;
	}

	// método onde definimos o que será escrito quando chamarmos o println(objeto)
	@Override
	public String toString() {
		return "Usuário " + name + ", com " + age + " anos, registrado em " + registerDate + ".";
	}

}
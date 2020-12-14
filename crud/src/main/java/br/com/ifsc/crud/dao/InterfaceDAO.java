package br.com.ifsc.crud.dao;


import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;

// uma interface com um tipo genérico para usarmos em nossos DAOs, cada implementação da interface deverá indicar o tipo da entidade que ela implementará o DAO
public interface InterfaceDAO<T> {

	public T get(String id);

	public List<T> getAll()  throws UnknownHostException, IOException;

	public void add(T obj);

	public void delete(T obj);

	public void update(T obj);
}

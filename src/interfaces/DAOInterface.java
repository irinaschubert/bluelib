package interfaces;

/*
 * @version 0.1 16.10.2018
 * @author Schmutz
 */

import java.util.List;

public interface DAOInterface <T>{
	public T save(T domainObject);
	public T update(T domainObject);
	public boolean delete(T domainObject);
	public List<T> getSelektion(T domainObject);
	public T findById(int id);
	public List<T> findAll();
		
}

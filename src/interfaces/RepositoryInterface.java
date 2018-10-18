package interfaces;


/**
 * @version 0.1 16.10.2018
 * @author Schmutz
 */
import java.util.List;

public interface RepositoryInterface <T>{
	
	public T add(T domainObject);
	public T update(T domainObject);
	public boolean remove(T domainObject);
	public List<T> query(T domainObject);
	public T findById(int id);
	public List<T> findAll();
	
	
}

package repositories;

import java.sql.SQLException;
import java.util.List;

import dao.AnredeDAO;
import domain.Anrede;
import interfaces.RepositoryInterface;

public class AnredeRepository implements RepositoryInterface<Anrede> {

	@Override
	public Anrede add(Anrede domainObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Anrede update(Anrede domainObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean remove(Anrede domainObject) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Anrede> query(Anrede domainObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Anrede findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Anrede> findAll()  {
		AnredeDAO anredeDAO = new AnredeDAO();
		return anredeDAO.findAll();
		
	}

}

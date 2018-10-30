package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import domain.Buch;
import hilfsklassen.DateConverter;
import interfaces.DAOInterface;
/**
 * @version 0.1 28.10.2018
 * @author Mike
 *
 */
public class BuchDAO implements DAOInterface<Buch> {

	@Override
	public Buch save(Buch domainObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Buch update(Buch domainObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(Buch domainObject) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Buch> getSelektion(Buch domainObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Buch findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Buch> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
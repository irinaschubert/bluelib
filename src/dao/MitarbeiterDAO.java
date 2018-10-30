package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import domain.Mitarbeiter;
import hilfsklassen.DateConverter;
import interfaces.DAOInterface;
/**
 * @version 0.1 28.10.2018
 * @author Mike
 *
 */
public class MitarbeiterDAO implements DAOInterface<Mitarbeiter> {

	@Override
	public Mitarbeiter save(Mitarbeiter domainObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mitarbeiter update(Mitarbeiter domainObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(Mitarbeiter domainObject) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Mitarbeiter> getSelektion(Mitarbeiter domainObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mitarbeiter findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Mitarbeiter> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
	
}

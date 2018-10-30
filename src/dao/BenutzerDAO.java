package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import domain.Benutzer;
import hilfsklassen.DateConverter;
import interfaces.DAOInterface;

/**
 * @version 0.1 28.10.2018
 * @author Mike
 *
 */
public class BenutzerDAO implements DAOInterface<Benutzer> {

	@Override
	public Benutzer save(Benutzer domainObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Benutzer update(Benutzer domainObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(Benutzer domainObject) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Benutzer> getSelektion(Benutzer domainObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Benutzer findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Benutzer> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}

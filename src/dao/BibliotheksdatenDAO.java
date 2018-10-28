package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import domain.Bibliothek;
import hilfsklassen.DateConverter;
import interfaces.DAOInterface;
/**
 * @version 0.1 28.10.2018
 * @author Mike
 *
 */
public class BibliotheksdatenDAO implements DAOInterface<Bibliothek> {

	@Override
	public Bibliothek save(Bibliothek domainObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Bibliothek update(Bibliothek domainObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(Bibliothek domainObject) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Bibliothek> getSelektion(Bibliothek domainObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Bibliothek findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Bibliothek> findAll() {
		// TODO Auto-generated method stub
		return null;
	}


}

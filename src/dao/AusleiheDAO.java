package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import domain.Ausleihe;
import domain.Verlag;
import hilfsklassen.DateConverter;
import interfaces.DAOInterface;
/**
 * @version 0.1 28.10.2018
 * @author Mike
 *
 */
public class AusleiheDAO implements DAOInterface<Ausleihe> {

	private DBConnection dbConnection = null;
	private Connection conn = null; 
	private ResultSet mRS = null;
	private PreparedStatement pstmt = null;
	private List<Ausleihe> ausleiheListe = null;
	
	
	public AusleiheDAO() {
		ausleiheListe = new ArrayList<>();
		dbConnection = DBConnection.getInstance();
	}
	
	@Override
	public Ausleihe save(Ausleihe domainObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ausleihe update(Ausleihe domainObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(Ausleihe domainObject) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Ausleihe> getSelektion(Ausleihe domainObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ausleihe findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Ausleihe> findAll() {
		// TODO Auto-generated method stub
		return null;
	}


}

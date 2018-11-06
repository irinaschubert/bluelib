package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import domain.Autor;
import domain.Benutzer;
import hilfsklassen.DateConverter;
import interfaces.DAOInterface;

/**
 * Verwaltet die CRUD-Operationen für Benutzer
 * @version 0.1 06.11.2018
 * @author irina
 *
 */
public class BenutzerDAO implements DAOInterface<Benutzer> {
	
	private DBConnection dbConnection = null;
	private Connection conn = null; 
	private PreparedStatement pstmt = null;
	private List<Benutzer> benutzerListe = null;
	
	public BenutzerDAO () {
		benutzerListe = new ArrayList<>();
		dbConnection = DBConnection.getInstance();
	}

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

package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domain.Anrede;
import interfaces.DAOInterface;

/**
 * Holt sich die Anrededaten
 * 
 * Die SQL-Fehler müssen in Try-Catch-Blöcken abgefangen werden throws Excecption, würde die SQL-Fehler durch
 * alle nachfolgenden Klassen weiterziehen und müsste im Interface implementiert werden
 * 
 * @version 0.1 16.10.2018
 * @author Schmutz
 */

public class AnredeDAO implements DAOInterface<Anrede> {

	private Connection conn = null; 
	private ResultSet mRS = null;
	private PreparedStatement pstmt = null;
	private List<Anrede> anredeListe = null;
	
	public AnredeDAO(){
		anredeListe = new ArrayList<Anrede>();
	}
	
	@Override
	public Anrede save(Anrede domainObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Anrede update(Anrede domainObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(Anrede domainObject) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Anrede> getSelektion(Anrede domainObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Anrede findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Anrede> findAll() {

		String sql = "SELECT * "
				+ "FROM anrede";
	
			try {
				
				conn = DBConnection.getDBConnection();
				pstmt = conn.prepareStatement(sql);
				mRS = pstmt.executeQuery();
				while(mRS.next()) {
					 Anrede a = new Anrede();
					 a.setId(mRS.getInt(1));
					 a.setBezeichnung(mRS.getString(2));
					 anredeListe.add(a);}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}

			finally {
						try {
							DBConnection.closeConnection(conn);
						} catch (SQLException e) {
							e.printStackTrace();
						}
			
			}
			
			return anredeListe;
	}

	

	

}

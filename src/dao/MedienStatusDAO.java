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
import domain.Medium;
import domain.Status;
import hilfsklassen.DateConverter;
import interfaces.DAOInterface;
/**
 * Stellt die CRUD-Operationen der Medienstatus-Objekte zur Verfügung
 * 
 * @version 0.1 28.10.2018
 * @author Mike
 * */
public class MedienStatusDAO implements DAOInterface<Status> {
	private DBConnection dbConnection = null;
	private Connection conn = null; 
	private PreparedStatement pstmt = null;
	private List<Status> statusListe = null;
	
	
	public MedienStatusDAO() {
		statusListe = new ArrayList<>();
		dbConnection = DBConnection.getInstance();
	}
	@Override
	public Status save(Status domainObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Status update(Status domainObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(Status domainObject) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Status> getSelektion(Status domainObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Status findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Status> findAll() {
		ResultSet rs = null;
		
		String sql = "SELECT "
				+ "id, "
				+ "bezeichnung "
				+ "FROM statusmedi";
			try {
				
				conn = dbConnection.getDBConnection();
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				while(rs.next()) {
					int counter = 1;
					 Status a = new Status();
					 a.setId(rs.getInt(counter++));
					 a.setBezeichnung(rs.getString(counter++));
					statusListe.add(a);}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}

	    finally{
	         try{
	             if(rs != null) rs.close();
	             if(pstmt != null) pstmt.close();
	             if(conn != null) conn.close();
	         } catch(Exception ex){}
	     }
			
			return statusListe;
	}
	


}

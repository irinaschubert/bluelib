package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import domain.DezKlassifikationGrpe;
import interfaces.DAOInterface;

/**
 * Stellt die CRUD-Operationen der Dezimalklassifikationgruppen-Objekte zur Verfügung
 * 
 * @version 1.0 2018-11-03
 * @author Ueli
 *
 */


public class DezKlassifikationGrpeDAO implements DAOInterface<DezKlassifikationGrpe> {
	
	private DBConnection dbConnection = null;
	private Connection conn = null; 
	private PreparedStatement pstmt = null;
	private List<DezKlassifikationGrpe> dezKlassGrpeListe = null;
	
	
	public DezKlassifikationGrpeDAO() {
		dezKlassGrpeListe = new ArrayList<>();
		dbConnection = DBConnection.getInstance();
	}

	@Override
	public DezKlassifikationGrpe save(DezKlassifikationGrpe domainObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DezKlassifikationGrpe update(DezKlassifikationGrpe domainObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(DezKlassifikationGrpe domainObject) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<DezKlassifikationGrpe> getSelektion(DezKlassifikationGrpe domainObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DezKlassifikationGrpe findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DezKlassifikationGrpe> findAll() {
	ResultSet rs = null;
		
		String sql = "SELECT "
				+ "id, "
				+ "gruppe, "
				+ "bezeichnung "
				+ "FROM dezgrp "
				+ "ORDER BY gruppe, bezeichnung";
			try {
				
				conn = dbConnection.getDBConnection();
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				while(rs.next()) {
					 DezKlassifikationGrpe d = new DezKlassifikationGrpe();
					 d.setId(rs.getInt(1));
					 d.setGruppe(rs.getInt(2));
					 d.setBezeichnung(rs.getString(3));
					 dezKlassGrpeListe.add(d);
					 }
				
			} catch (SQLException e) {
				e.printStackTrace();
			}

			finally {
						try {
							dbConnection.closeConnection(conn);
						} catch (SQLException e) {
							e.printStackTrace();
						}
			
			}
			
			return dezKlassGrpeListe;
	}

}

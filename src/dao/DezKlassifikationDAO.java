package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import domain.DezKlassifikation;
import interfaces.DAOInterface;

public class DezKlassifikationDAO implements DAOInterface<DezKlassifikation> {
	
	private DBConnection dbConnection = null;
	private Connection conn = null; 
	private PreparedStatement pstmt = null;
	private List<DezKlassifikation> dezKlassListe = null;
	
	
	public DezKlassifikationDAO() {
		dezKlassListe = new ArrayList<>();
		dbConnection = DBConnection.getInstance();
	}

	@Override
	public DezKlassifikation save(DezKlassifikation domainObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DezKlassifikation update(DezKlassifikation domainObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(DezKlassifikation domainObject) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<DezKlassifikation> getSelektion(DezKlassifikation domainObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DezKlassifikation findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DezKlassifikation> findAll() {
	ResultSet rs = null;
		
		String sql = "SELECT "
				+ "id, "
				+ "dezGrp_id, "
				+ "dezklasse,"
				+ "bezeichnung "
				+ "FROM dezkla "
				+ "ORDER BY dezklasse, bezeichnung";
			try {
				
				conn = dbConnection.getDBConnection();
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				while(rs.next()) {
					 DezKlassifikation d = new DezKlassifikation();
					 d.setId(rs.getInt(1));
					 d.setGruppeId(rs.getInt(2));
					 d.setDezKlasse(rs.getString(3));
					 d.setBezeichnung(rs.getString(4));
					 dezKlassListe.add(d);
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
			
			return dezKlassListe;
	}

}

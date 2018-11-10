package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domain.Mitarbeiter;
import hilfsklassen.SQLHelfer;
import interfaces.DAOInterface;
import services.HashRechner;
/**
 * @version 0.1 28.10.2018
 * @author Mike
 *
 */
public class MitarbeiterDAO implements DAOInterface<Mitarbeiter> {

	private DBConnection dbConnection = null;
	private Connection conn = null; 
	private PreparedStatement pstmt = null;
	private List<Mitarbeiter> mitarbeiterListe = null;
	
	
	public MitarbeiterDAO() {
		mitarbeiterListe = new ArrayList<>();
		dbConnection = DBConnection.getInstance();
	}
	
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
		
		ResultSet rs = null;
		String sql = "SELECT "
				+ "id, "
				+ "benutzername, "
				+ "passwort, "
				+ "admin, "
				+ "aktiv "
				+ "FROM mitarbeiter ";
		
				// Admin-Flag wird immer abgefragt, daher mit WHERE
				sql = sql + ("WHERE admin = ? ");		
				
				if (domainObject.getBenutzername() != null) {
					sql = sql + "AND benutzername";
					sql = sql + (SQLHelfer.likePruefung(domainObject.getBenutzername())?" LIKE": " =");
					sql = sql + " ?";
				}
				// Passwort darf nie mit Wildcard abgefragt werden
				if (domainObject.getPasswort() != null) {
					sql = sql + ("AND passwort = ? ");
				}
				
				// Status ist immer gesetzt
				sql = sql + ("AND aktiv = ?");
	
			try {
				
				int pCounter = 1;
				conn = dbConnection.getDBConnection();
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setBoolean(pCounter++, domainObject.isAdmin());
				;
				
				if (domainObject.getVorname() != null) {
					pstmt.setString(pCounter++,SQLHelfer.SternFragezeichenErsatz(domainObject.getBenutzername()));
					
				}
				if (domainObject.getPasswort() != null) {
					pstmt.setString(pCounter++,HashRechner.hashBerechnen(domainObject.getBenutzername()));
					
				}
				pstmt.setBoolean(pCounter++, domainObject.isAktiv());
				

				rs = pstmt.executeQuery();
				pCounter = 0;
				while(rs.next()) {
					 Mitarbeiter m = new Mitarbeiter();
					 m.setId(rs.getInt(pCounter++));
					 m.setBenutzername(rs.getString(pCounter++));
					 m.setPasswort(rs.getString(pCounter++));
					 m.setAdmin(rs.getBoolean(pCounter++));
					 m.setAktiv(rs.getBoolean(pCounter++));
					 mitarbeiterListe.add(m);
					 }
				
			} catch (SQLException e) {
				e.printStackTrace();
	

				   } finally{
				         try{
				             if(rs != null) rs.close();
				             if(pstmt != null) pstmt.close();
				             if(conn != null) conn.close();
				         } catch(Exception ex){}
				     }
			
			return mitarbeiterListe;
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
	
	public Boolean loginPruefung(String benutzername, String passwort) {
		Boolean darfEinloggen = false; 
		ResultSet rs = null;
		String sql = "SELECT "
				+ "id "
				+ "FROM mitarbeiter "
				+ "WHERE aktiv = TRUE "
				+ "AND benutzername = ? "
				+ "AND passwort = ?";
	
			try {
				
				int pCounter = 1;
				conn = dbConnection.getDBConnection();
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(pCounter++, benutzername);
				pstmt.setString(pCounter++, HashRechner.hashBerechnen(passwort));

				rs = pstmt.executeQuery();
				darfEinloggen = (rs.next()?true:false);
				
				
			} catch (SQLException e) {
				e.printStackTrace();
	

				   } finally{
				         try{
				             if(rs != null) rs.close();
				             if(pstmt != null) pstmt.close();
				             if(conn != null) conn.close();
				         } catch(Exception ex){}
				     }
			
			return darfEinloggen;
	}
	
}

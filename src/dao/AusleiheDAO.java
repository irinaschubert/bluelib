package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import domain.Ausleihe;
import domain.Benutzer;
import hilfsklassen.DateConverter;
import interfaces.DAOInterface;

/**
 * Verwaltet die CRUD- und weitere Operationen f�r Ausleihe-Objekte.
 * 
 * @version 0.1 06.11.2018
 * @author Irina, Ueli
 * 
 */
public class AusleiheDAO implements DAOInterface<Ausleihe> {

	private DBConnection dbConnection = null;
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private PreparedStatement pstmt2 = null;
	private List<Ausleihe> ausleiheListe = null;

	public AusleiheDAO() {
		ausleiheListe = new ArrayList<>();
		dbConnection = DBConnection.getInstance();
	}

	@Override
	public Ausleihe save(Ausleihe domainObject) {
		ResultSet rs = null;
		Ausleihe a = null;
		int argCounter = 0;
		String sql = " INSERT INTO " 
				+ "ausleihe " 
				+ "(person_id," 
				+ " medium_id," 
				+ " von"
				+ (domainObject.getRueckgabeDatum() != null ? ", retour" : "") 
				+ ", erfasser_person_id"
				+ (domainObject.getRueckgabeMitarbeiterID() > 0 ? ", retour_person_id" : "") 
				+ ") " 
				+ "VALUES "
				+ "(?, ?, ?" 
				+ (domainObject.getRueckgabeDatum() != null ? ",? " : "") 
				+ ", ?"
				+ (domainObject.getRueckgabeMitarbeiterID() > 0 ? ",? " : "") 
				+ ")";
				
		try {
			conn = dbConnection.getDBConnection();
			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			argCounter++;
			pstmt.setInt(argCounter, domainObject.getBenutzer().getId());
			argCounter++;
			pstmt.setInt(argCounter, domainObject.getMedium().getId());
			argCounter++;
			pstmt.setDate(argCounter, DateConverter.convertJavaDateToSQLDateN(domainObject.getAusleiheDatum()));
			if (domainObject.getRueckgabeDatum() != null) {
				argCounter++;
				pstmt.setDate(argCounter, DateConverter.convertJavaDateToSQLDateN(domainObject.getRueckgabeDatum()));
			}
			argCounter++;
			pstmt.setInt(argCounter, domainObject.getAusleiheMitarbeiterID());
			if (domainObject.getRueckgabeMitarbeiterID() > 0) {
				argCounter++;
				pstmt.setInt(argCounter, domainObject.getRueckgabeMitarbeiterID());
			}
			pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys();
			if (rs != null && rs.next()) {
				a = new AusleiheDAO().findById(rs.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		argCounter = 0;
		String sql2 = " UPDATE medium SET bemerkung="
				+ (domainObject.getMedium().getBemerkung() != null  ? "?" : "")
				+ " WHERE id = " 
				+ domainObject.getMedium().getId() ;
		try {
			conn = dbConnection.getDBConnection();
			pstmt2 = conn.prepareStatement(sql2, Statement.RETURN_GENERATED_KEYS);
			if (domainObject.getMedium().getBemerkung() != "" || !domainObject.getMedium().getBemerkung().isEmpty()) {
				argCounter++;
				pstmt2.setString(argCounter, domainObject.getMedium().getBemerkung());
			}
			else {
				argCounter++;
				pstmt2.setString(argCounter, "");
			}
			pstmt2.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt2 != null)
					pstmt2.close();
				if (conn != null)
					conn.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		
		return a;
	}

	
	// Es erfolgen nur Updates auf das R�ckgabedatum und den Erfasser der R�ckgabe
	@Override
	public Ausleihe update(Ausleihe domainObject) {
		Ausleihe a = null;
		int updateErfolreich = 0;
		int argCounter = 1;
		String sql = "UPDATE ausleihe "
			+ "SET "
			+ (domainObject.getRueckgabeDatum() != null ? " retour = ? ":" ")
			+ (domainObject.getRueckgabeMitarbeiterID() > 0 ? ", retour_person_id = ? ":" ")
			+ "WHERE id = ?";

		try {
			conn = dbConnection.getDBConnection();
			pstmt = conn.prepareStatement(sql);
			if (domainObject.getRueckgabeDatum() != null) {
				pstmt.setDate(argCounter++,DateConverter.convertJavaDateToSQLDateN(domainObject.getRueckgabeDatum()));
			}
				if (domainObject.getRueckgabeMitarbeiterID() > 0) {
				pstmt.setInt(argCounter++,domainObject.getRueckgabeMitarbeiterID());
			}
				
				pstmt.setInt(argCounter++, domainObject.getId());
				
			updateErfolreich = pstmt.executeUpdate();
			if (updateErfolreich > 0) {
				a = domainObject;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		} 
		finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} 
			catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return a;
	}

	@Override
	public boolean delete(Ausleihe domainObject) {
		return false;
	}

	public List<Ausleihe> getSelektionByBenutzer(Benutzer domainObject) {
		ResultSet rs = null;
		String sql = "SELECT " 
				+ "id, " 
				+ "medium_id, " 
				+ "von " 
				+ "FROM ausleihe ";
		sql = sql + ("where person_id = ? AND retour is null");
		try {
			int pCounter = 1;
			conn = dbConnection.getDBConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(pCounter, domainObject.getId());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Ausleihe a = new Ausleihe();
				a = findById(rs.getInt(1));
				ausleiheListe.add(a);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return ausleiheListe;
	}

	@Override
	public Ausleihe findById(int id) {
		Ausleihe a = new Ausleihe();
		BuchDAO buchDAO = new BuchDAO();
		BenutzerDAO benutzerDAO = new BenutzerDAO();
		ResultSet rs = null;
		String sql = "SELECT " 
				+ "id, person_id, medium_id, von, retour, " 
				+ "erfasser_person_id, retour_person_id "
				+ "FROM ausleihe " 
				+ "WHERE id = ?";
		try {
			conn = dbConnection.getDBConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				a.setId(rs.getInt(1));
				a.setBenutzer(benutzerDAO.findById(rs.getInt(2)));
				a.setMedium(buchDAO.findById(rs.getInt(3)));
				a.setAusleiheDatum(rs.getDate(4));
				if (rs.getDate(5) != null) {
					a.setRueckgabeDatum(rs.getDate(5));
				} else {
					a.setRueckgabeDatum(null);
				}
				a.setAusleiheMitarbeiterID(rs.getInt(6));
				a.setRueckgabeMitarbeiterID(rs.getInt(7));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return a;
	}

	@Override
	public ArrayList<Ausleihe> findAll() {
		ArrayList<Ausleihe> ausleihen = new ArrayList<>();
		ResultSet rs = null;
		String sql = "SELECT id from ausleihe";
		try {
			conn = dbConnection.getDBConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Ausleihe a = new Ausleihe();
				a = findById(rs.getInt(1));
				ausleihen.add(a);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return ausleihen;
	}

	@Override
	public List<Ausleihe> getSelektion(Ausleihe domainObject) {
		ResultSet rs = null;
		Ausleihe ausleihe = null;
		String sql = "SELECT "
				+ "id "
				+ ",person_id "
				+ ",medium_id "
				+ ",von "
				+ ",retour "
				+ ",erfasser_person_id "
				+ ",retour_person_id "
				+ "FROM ausleihe "
				+ "WHERE retour = ?";
		// TODO Where-Statement muss noch fertigtestellt werden
		try {

			conn = dbConnection.getDBConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setDate(1,DateConverter.convertJavaDateToSQLDateN(domainObject.getRueckgabeDatum()));
			rs = pstmt.executeQuery();
			
			MitarbeiterDAO mitarbeiterDAO = new MitarbeiterDAO();
			while(rs.next()) {
				int count = 1;
				ausleihe = new Ausleihe();
				ausleihe.setId(rs.getInt(count++));
				BenutzerDAO benutzerDAO = new BenutzerDAO();
				ausleihe.setBenutzer(benutzerDAO.findById(rs.getInt(count++)));
				BuchDAO buchDAO = new BuchDAO();
				ausleihe.setMedium(buchDAO.findById(rs.getInt(count++)));
				ausleihe.setAusleiheDatum(rs.getDate(count++));
				ausleihe.setRueckgabeDatum(rs.getDate(count++));
				ausleihe.setAusleiheMitarbeiterID(rs.getInt(count++));
				ausleihe.setRueckgabeMitarbeiterID(rs.getInt(count++));
				String name = mitarbeiterDAO.findById(ausleihe.getAusleiheMitarbeiterID()).getName();
				ausleihe.setAusleiheMitarbeiterName(name);
				ausleiheListe.add(ausleihe);
			}

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {

			try{
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch(Exception ex){}
		}
		return ausleiheListe;
	}
	
	/**
	 * @param id Buch
	 * @return Ausleihe, falls das Buch ausgeliehen ist, sonst null;
	 */
	public Ausleihe findAusgeliehenesBuchById(int id) {
		ResultSet rs = null;
		Ausleihe ausleihe = null;
		String sql = "SELECT "
				+ "id "
				+ ",person_id "
				+ ",medium_id "
				+ ",von "
				+ ",erfasser_person_id "
				+ "FROM ausleihe "
				+ "WHERE medium_id = ? "
				+ "AND von IS NOT NULL "
				+ "AND retour IS NULL";
		try {

			conn = dbConnection.getDBConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();

			ausleihe = new Ausleihe();
			MitarbeiterDAO mitarbeiterDAO = new MitarbeiterDAO();
			while (rs.next()) {
				int count = 1;
				ausleihe.setId(rs.getInt(count++));
				BenutzerDAO benutzerDAO = new BenutzerDAO();
				ausleihe.setBenutzer(benutzerDAO.findById(rs.getInt(count++)));
				BuchDAO buchDAO = new BuchDAO();
				ausleihe.setMedium(buchDAO.findById(rs.getInt(count++)));
				ausleihe.setAusleiheDatum(rs.getDate(count++));
				ausleihe.setAusleiheMitarbeiterID(rs.getInt(count++));
				String name = mitarbeiterDAO.findById(ausleihe.getAusleiheMitarbeiterID()).getName();
				ausleihe.setAusleiheMitarbeiterName(name);
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
		
		return ausleihe;
	}
	
	/**
	 * @param id Buch
	 * @return true, falls das Buch ausgeliehen ist, sonst null;
	 */
	public Boolean mediumIstAusgeliehen(int id) {
		ResultSet rs = null;
		Boolean mediumAusgeliehen = false;
		String sql = "SELECT 1 FROM ausleihe "
				+ "WHERE medium_id = ? "
				+ "AND von IS NOT NULL "
				+ "AND retour IS NULL";
		try {
			conn = dbConnection.getDBConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				mediumAusgeliehen = true;
			}

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {

			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception ex) {
			}
		}

		return mediumAusgeliehen;
	}
	
	
	public boolean deleteByAusleihe(Ausleihe domainObject) {
		ResultSet rs = null;
		boolean geloescht = false;
		String sql = "DELETE FROM ausleihe WHERE id = ?";
			try {
				conn = dbConnection.getDBConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, domainObject.getId());
				int i = pstmt.executeUpdate();
				if (i > 0) {
					geloescht = true;
				}			
			}
			catch (SQLException e) {
				e.printStackTrace();
			} 
			finally {
				try {
					if(rs != null) rs.close();
					if(pstmt != null) pstmt.close();
					if(conn != null) conn.close();
				} 
				catch(Exception ex){
					ex.printStackTrace();
				}
			}
			return geloescht;
	}
}

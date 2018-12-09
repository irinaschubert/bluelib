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
 * Verwaltet die CRUD- und weitere Operationen für Ausleihe-Objekte
 * 
 * @version 0.1 06.11.2018
 * @author irina
 */
public class AusleiheDAO implements DAOInterface<Ausleihe> {

	private DBConnection dbConnection = null;
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private List<Ausleihe> ausleiheListe = null;

	public AusleiheDAO() {
		ausleiheListe = new ArrayList<>();
		dbConnection = DBConnection.getInstance();
	}

	@Override
	public Ausleihe save(Ausleihe domainObject) {
		ResultSet rs = null;
		Ausleihe a = new Ausleihe();
		int argCounter = 0;
		String sql = "INSERT INTO " + "ausleihe " + "(person_id," + " medium_id," + " von"
				+ (domainObject.getRueckgabeDatum() != null ? ", retour" : "") + ", erfasser_person_id"
				+ (domainObject.getRueckgabeMitarbeiterID() > 0 ? ", retour_person_id" : "") + ") " + "VALUES "
				+ "(?, ?, ?" + (domainObject.getRueckgabeDatum() != null ? ",? " : "") + ", ?"
				+ (domainObject.getRueckgabeMitarbeiterID() > 0 ? ",? " : "") + ")";
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
			System.out.println(pstmt);
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
				if (conn != null)
					conn.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return a;
	}

	@Override
	public Ausleihe update(Ausleihe domainObject) {
		return null;
	}

	@Override
	public boolean delete(Ausleihe domainObject) {
		return false;
	}

	public List<Ausleihe> getSelektionByBenutzer(Benutzer domainObject) {
		ResultSet rs = null;
		String sql = "SELECT " + "id, " + "medium_id, " + "von " + "FROM ausleihe ";
		sql = sql + ("where person_id = ?");
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
		String sql = "SELECT " + "id, person_id, medium_id, von, retour, " + "erfasser_person_id, retour_person_id "
				+ "FROM ausleihe " + "WHERE id = ?";
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
		return null;
	}
}

package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import domain.Adresse;
import domain.Autor;
import domain.Bibliothek;
import domain.Ort;
import hilfsklassen.DateConverter;
import interfaces.DAOInterface;

/**
 * Stellt die CRUD- und weitere Operationen zum Verwalten des Bibliothekobjekts zur Verfügung.
 * 
 * @version 0.1 28.10.2018
 * @author Mike
 *
 */
public class BibliothekDAO implements DAOInterface<Bibliothek> {

	private DBConnection dbConnection = null;
	private Connection conn = null;
	private PreparedStatement pstmt = null;

	public BibliothekDAO() {
		dbConnection = DBConnection.getInstance();
	}

	@Override
	public Bibliothek save(Bibliothek domainObject) {
		// Kein Save, nur Update
		return null;
	}

	@Override
	public Bibliothek update(Bibliothek domainObject) {
		ResultSet rs = null;
		Bibliothek b = new Bibliothek();
		String sql = "UPDATE stammdaten SET " 
				+ "name  = ?" 
				+ ", strasseUndNr  = ?" 
				+ ", ort_id = ?"
				+ ", email  = ?" 
				+ ", telefon = ?"
				+ ", leihfrist = ? ";
		try {
			conn = dbConnection.getDBConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, domainObject.getName());
			pstmt.setString(2, domainObject.getAdresse().getStrasse());
			pstmt.setInt(3, domainObject.getAdresse().getOrt().getId());
			pstmt.setString(4, domainObject.getEmail());
			pstmt.setString(5, domainObject.getTelefon());
			pstmt.setInt(6, domainObject.getLeihfrist());

			int i = pstmt.executeUpdate();
			if (i > 0) {
				b = domainObject;
			} else {
				b = null;
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
		return b;
	}

	@Override
	public boolean delete(Bibliothek domainObject) {
		return false;
	}

	@Override
	public List<Bibliothek> getSelektion(Bibliothek domainObject) {
		return null;
	}

	@Override
	public Bibliothek findById(int id) {
		Bibliothek b = new Bibliothek();
		ResultSet rs = null;
		String sql = "SELECT " 
				+ "id, " 
				+ "name, " 
				+ "strasseUndNr, "
				+ "ort_id, "
				+ "email, " 
				+ "telefon, " 
				+ "leihfrist "
				+ "FROM stammdaten " 
				+ "WHERE id = ?";
		try {
			conn = dbConnection.getDBConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				b.setId(rs.getInt(1));
				b.setName(rs.getString(2));
				int ortId = rs.getInt(4);
				OrtDAO ortDAO = new OrtDAO();
				Ort ort = ortDAO.findById(ortId);
				String strasse = rs.getString(3);
				Adresse adresse = new Adresse(strasse, ort);
				b.setAdresse(adresse);
				b.setEmail(rs.getString(5));
				b.setTelefon(rs.getString(6));
				b.setLeihfrist(rs.getInt(7));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return b;
	}

	@Override
	public List<Bibliothek> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}

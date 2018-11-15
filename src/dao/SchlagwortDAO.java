package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import domain.Schlagwort;
import interfaces.DAOInterface;

/**
 * @version 1 28.10.2018
 * @author Mike
 *
 */
public class SchlagwortDAO implements DAOInterface<Schlagwort> {

	private DBConnection dbConnection = null;
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private List<Schlagwort> schlagwortListe = null;

	public SchlagwortDAO() {
		schlagwortListe = new ArrayList<>();
		dbConnection = DBConnection.getInstance();
	}

	@Override
	public Schlagwort save(Schlagwort domainObject) {
		ResultSet rs = null;
		Schlagwort s = new Schlagwort();
		int argCounter = 0;
		String sql = "INSERT INTO " + "schlagwort " + "(schlagwort, " + "geloescht " + ") " + "VALUES " + "(?, ?)";
		try {
			conn = dbConnection.getDBConnection();
			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			argCounter++;
			pstmt.setString(argCounter, domainObject.getSchlagwort());
			argCounter++;
			pstmt.setBoolean(argCounter, domainObject.getGeloescht());
			pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys();
			if (rs != null && rs.next()) {
				s = new SchlagwortDAO().findById(rs.getInt(1));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
		return s;
	}

	@Override
	public Schlagwort update(Schlagwort domainObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(Schlagwort domainObject) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Schlagwort> getSelektion(Schlagwort domainObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Schlagwort findById(int id) {
		Schlagwort s = new Schlagwort();
		ResultSet rs = null;
		String sql = "SELECT " + "id, " + "schlagwort " + "FROM autor " + "WHERE id = ?";
		try {
			conn = dbConnection.getDBConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				s.setId(rs.getInt(1));
				s.setSchlagwort(rs.getString(2));
				s.setGeloescht(rs.getBoolean(3));
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
		return s;
	}

	@Override
	public List<Schlagwort> findAll() {
		ResultSet rs = null;
		String sql = "SELECT " + "id, " + "schlagwort, " + "geloescht " + "FROM schlagwort";
		try {
			conn = dbConnection.getDBConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Schlagwort s = new Schlagwort();
				s.setId(rs.getInt(1));
				s.setSchlagwort(rs.getString(2));
				s.setGeloescht(rs.getBoolean(3));
				schlagwortListe.add(s);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				dbConnection.closeConnection(conn);
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		return schlagwortListe;
	}

}

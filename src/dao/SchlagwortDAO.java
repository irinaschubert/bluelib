package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import domain.Autor;
import domain.Schlagwort;
import hilfsklassen.DateConverter;
import hilfsklassen.SQLHelfer;
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
		System.out.println("dao save");
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
		ResultSet rs = null;
		Schlagwort s = new Schlagwort();
		String sql = "UPDATE schlagwort SET "
				+ "schlagwort = ? "
				+ ",geloescht = ?"
				+ " WHERE id = ?";
			try {
				conn = dbConnection.getDBConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1,domainObject.getSchlagwort());
				pstmt.setBoolean(2, domainObject.getGeloescht());
				pstmt.setInt(3,  domainObject.getId());
				int i = pstmt.executeUpdate();
				if (i>0) {
					s = domainObject;
				}
				else {
					s = null;
				}
								
			}
	  catch (SQLException e) {
           e.printStackTrace();
     } finally{
         try{
             if(rs != null) rs.close();
             if(pstmt != null) pstmt.close();
             if(conn != null) conn.close();
         } catch(Exception ex){}
     }
			
		return s;
	}

	@Override
	public boolean delete(Schlagwort domainObject) {
		ResultSet rs = null;
		boolean geloescht = false;
		String sql = "DELETE FROM "
				+ "schlagwort "
				+ "WHERE id = ?";
			try {
				
				conn = dbConnection.getDBConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, domainObject.getId());
				int i = pstmt.executeUpdate();
				if (i>0) {
					geloescht = true;
				}
								
			}
	  catch (SQLException e) {
           e.printStackTrace();
     } finally{
         try{
             if(rs != null) rs.close();
             if(pstmt != null) pstmt.close();
             if(conn != null) conn.close();
         } catch(Exception ex){}
     }
			
		return geloescht;
	}

	@Override
	public List<Schlagwort> getSelektion(Schlagwort domainObject) {
		ResultSet rs = null;
		int whereCounter = 1;
		String sql = "SELECT " + "id, " + "schlagwort, " + "geloescht " + "FROM schlagwort ";

		if (domainObject.getSchlagwort() != null) {
			sql = sql + "WHERE schlagwort";
			sql = sql + (SQLHelfer.likePruefung(domainObject.getSchlagwort()) ? " LIKE" : " =");
			sql = sql + " ?";
			whereCounter++;
		}
		sql = sql + (whereCounter > 1 ? " AND" : " WHERE");
		sql = sql + (" geloescht = ?");

		try {
			int pCounter = 1;
			conn = dbConnection.getDBConnection();
			pstmt = conn.prepareStatement(sql);
			if (domainObject.getSchlagwort() != null) {
				pstmt.setString(pCounter, SQLHelfer.SternFragezeichenErsatz(domainObject.getSchlagwort()));
				pCounter++;
			}
			pstmt.setBoolean(pCounter, domainObject.getGeloescht());

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
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception ex) {
			}
		}

		return schlagwortListe;
	}

	@Override
	public Schlagwort findById(int id) {
		Schlagwort s = new Schlagwort();
		ResultSet rs = null;
		String sql = "SELECT " + "id, " + "schlagwort, " + "geloescht " + "FROM schlagwort " + "WHERE id = ?";
		try {
			conn = dbConnection.getDBConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				System.out.println("getint for id: "+rs.getInt(1));
				System.out.println("getstring for schlgw: "+rs.getString(2));
				System.out.println("getbool for gel: "+rs.getBoolean(3));
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
	
	public List<Schlagwort> findeSchlagwoerterZuMedium(int id){
		ResultSet rs = null;
		String sql = "SELECT "
				+ "ms.schlagwort_id "
				+ "FROM mediumschlagwort ms "
				+ "WHERE ms.medium_id = ?";
			try {
				
				conn = dbConnection.getDBConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1,id);
				rs = pstmt.executeQuery();
				SchlagwortDAO schlagwortDAO = new SchlagwortDAO();
				while(rs.next()) {
					schlagwortListe.add(schlagwortDAO.findById(rs.getInt(1)));					
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
			
			return schlagwortListe;
				
				
			
	}

}

package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import domain.Verlag;
import hilfsklassen.DateConverter;
import hilfsklassen.SQLHelfer;
import interfaces.DAOInterface;
/**
 * Verwaltet die CRUD- und weitere Operationen für Verlage
 * @version 0.1 06.11.2018
 * @author irina
 */
public class VerlagDAO implements DAOInterface<Verlag> {
	private DBConnection dbConnection = null;
	private Connection conn = null; 
	private ResultSet mRS = null;
	private PreparedStatement pstmt = null;
	private List<Verlag> verlagListe = null;
	
	public VerlagDAO() {
		verlagListe = new ArrayList<>();
		dbConnection = DBConnection.getInstance();
	}
	
	@Override
	public Verlag save(Verlag domainObject) {
		ResultSet rs = null;
		Verlag v = new Verlag();
		int argCounter = 0;
		String sql = "INSERT INTO "
				+ "verlag "
				+ "(name"
				+ (domainObject.getGruendungsDatum() != null ? ", gruendungsdatum":"")
				+ (domainObject.getEndDatum() != null?", enddatum":"")
				+ ", geloescht"
				+ ") "
				+ "VALUES "
				+ "(?"
				+ (domainObject.getGruendungsDatum() != null?", ?":"")
				+ (domainObject.getEndDatum()!= null?", ?":"")
				+ ", ?)";
			try {
				conn = dbConnection.getDBConnection();
				pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				argCounter++;
				pstmt.setString(argCounter, domainObject.getName());
				if (domainObject.getGruendungsDatum() != null) {
					argCounter++;
					pstmt.setDate(argCounter, DateConverter.convertJavaDateToSQLDateN(domainObject.getGruendungsDatum()));
				}
				if (domainObject.getEndDatum() != null) {
					argCounter++;
					pstmt.setDate(argCounter, DateConverter.convertJavaDateToSQLDateN(domainObject.getEndDatum()));
				}
				argCounter++;
				pstmt.setBoolean(argCounter, domainObject.getGeloescht());
				pstmt.executeUpdate();
				
				rs = pstmt.getGeneratedKeys();
				if(rs != null && rs.next()){
					v = new VerlagDAO().findById(rs.getInt(1));
				}
			}
			catch(SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					if(rs != null) rs.close();
					if(pstmt != null) pstmt.close();
					if(conn != null) conn.close();
				} 
				catch(Exception ex) {
					ex.printStackTrace();
				}
			}
			return v;
	}

	@Override
	public Verlag update(Verlag domainObject) {
		ResultSet rs = null;
		Verlag v = new Verlag();
		String sql = "UPDATE verlag SET"
				+ " name = ?"
				+ ", gruendungsdatum = ?"
				+ ", enddatum = ?"
				+ ", geloescht = ?"
				+ " WHERE id = ?";
			try {
				conn = dbConnection.getDBConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, domainObject.getName());
				
				if (domainObject.getGruendungsDatum() != null) {
				pstmt.setDate(2, DateConverter.convertJavaDateToSQLDateN(domainObject.getGruendungsDatum()));
				}
				else {
					pstmt.setNull(2, java.sql.Types.DATE);
				}
				
				if (domainObject.getEndDatum() != null) {
				pstmt.setDate(3, DateConverter.convertJavaDateToSQLDateN(domainObject.getEndDatum()));
				}
				else {
					pstmt.setNull(3, java.sql.Types.DATE);
				}
				pstmt.setBoolean(4, domainObject.getGeloescht());
				pstmt.setInt(5, domainObject.getId());
				int i = pstmt.executeUpdate();
				if (i>0) {
					v = domainObject;
				}
				else {
					v = null;
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
				catch(Exception ex) {
					ex.printStackTrace();
				}
			}
			return v;
	}

	@Override
	public boolean delete(Verlag domainObject) {
		ResultSet rs = null;
		boolean geloescht = false;
		String sql = "DELETE FROM verlag WHERE id = ?";
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

	@Override
	public List<Verlag> getSelektion(Verlag domainObject) {
		ResultSet rs = null;
		int whereCounter = 1;
		String sql = "SELECT"
			+ " id"
			+ ", name"
			+ ", gruendungsdatum"
			+ ", enddatum"
			+ ", geloescht"
			+ " FROM verlag";
			if (domainObject.getName() != null) {
				sql = sql + " WHERE name";
				sql = sql + (SQLHelfer.likePruefung(domainObject.getName())?" LIKE": " =");
				sql = sql + " ?";
				whereCounter++;
			}
			if (domainObject.getGruendungsDatum() != null) {
				sql = sql + (whereCounter > 1?" AND": " WHERE");
				sql = sql + (" gruendungsdatum = ?");
				whereCounter++;
			}
			if (domainObject.getEndDatum() != null) {
				sql = sql + (whereCounter > 1?" AND": " WHERE");
				sql = sql + (" enddatum = ?");
				whereCounter++;
			}
			sql = sql + (whereCounter > 1?" AND": " WHERE");
			sql = sql + (" geloescht = ?");
			try {
				int pCounter = 1;
				conn = dbConnection.getDBConnection();
				pstmt = conn.prepareStatement(sql);
				if (domainObject.getName() != null) {
					pstmt.setString(pCounter, SQLHelfer.SternFragezeichenErsatz(domainObject.getName()));
					pCounter++;
				}
				if (domainObject.getGruendungsDatum() != null) {
					pstmt.setDate(pCounter, DateConverter.convertJavaDateToSQLDateN(domainObject.getGruendungsDatum()));
					pCounter++;
				}
				if (domainObject.getEndDatum() != null) {
					pstmt.setDate(pCounter, DateConverter.convertJavaDateToSQLDateN(domainObject.getEndDatum()));
					pCounter++;
				}
				pstmt.setBoolean(pCounter, domainObject.getGeloescht());
				
				rs = pstmt.executeQuery();
				while(rs.next()) {
					Verlag v = new Verlag();
					v.setId(rs.getInt(1));
					v.setName(rs.getString(2));
					v.setGruendungsDatum(rs.getDate(3));
					v.setEndDatum(rs.getDate(4));
					v.setGeloescht(rs.getBoolean(5));
					verlagListe.add(v);
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
			return verlagListe;
	}

	@Override
	public Verlag findById(int id) {
		Verlag v = new Verlag();
		String sql = "SELECT"
			+ " id"
			+ ", name"
			+ ", gruendungsdatum"
			+ ", enddatum"
			+ ", geloescht"
			+ " FROM verlag"
			+ " WHERE id = ?";
			try {
				conn = dbConnection.getDBConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1,id);
				mRS = pstmt.executeQuery();
				while(mRS.next()) {
					 v.setId(mRS.getInt(1));
					 v.setName(mRS.getString(2));
					 v.setGruendungsDatum(mRS.getDate(3));
					 v.setEndDatum(mRS.getDate(4));
					 v.setGeloescht(mRS.getBoolean(5));
				}
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
			finally {
				try {
					conn.close();
				}
				catch (SQLException e) {
					e.printStackTrace();
				}
			}
			return v;
	}
	
	@Override
	public List<Verlag> findAll() {
		String sql = "SELECT"
			+ " id"
			+ ", name"
			+ ", gruendungsdatum"
			+ ", enddatum"
			+ ", geloescht"
			+ " FROM verlag";
			try {
				conn = dbConnection.getDBConnection();
				pstmt = conn.prepareStatement(sql);
				mRS = pstmt.executeQuery();
				while(mRS.next()) {
					 Verlag v = new Verlag();
					 v.setId(mRS.getInt(1));
					 v.setName(mRS.getString(2));
					 v.setGruendungsDatum(mRS.getDate(3));
					 v.setEndDatum(mRS.getDate(4));
					 v.setGeloescht(mRS.getBoolean(5));
					 verlagListe.add(v);
				}
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
			finally {
				try {
					dbConnection.closeConnection(conn);
				} 
				catch (SQLException e) {
					e.printStackTrace();
				}
			}
			return verlagListe;
	}
}

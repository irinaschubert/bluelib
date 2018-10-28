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
import hilfsklassen.DateConverter;
import interfaces.DAOInterface;

public class AutorDAO implements DAOInterface<Autor> {
	
	private DBConnection dbConnection = null;
	private Connection conn = null; 
	private ResultSet mRS = null;
	private PreparedStatement pstmt = null;
	private List<Autor> autorListe = null;
	
	
	public AutorDAO() {
		autorListe = new ArrayList<>();
		dbConnection = DBConnection.getInstance();
	}
	
	
	@Override
	public Autor save(Autor domainObject) {
		ResultSet rs = null;
		Autor a = new Autor();
		String sql = "INSERT INTO "
				+ "autor "
				+ "(vorname, "
				+ "nachname "
				+ (domainObject.getGeburtsdatum() != null ? ",geburtsdatum ":"")
				+ (domainObject.getTodesdatum() != null ? ",todesdatum ":"")
				+ ") "
				+ "VALUES "
				+ "(?, ? "
				+ (domainObject.getGeburtsdatum() != null?",? ":"")
				+ (domainObject.getTodesdatum() != null?",? ":"")
				+ ")" ;
			try {
				conn = dbConnection.getDBConnection();
				pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				pstmt.setString(1,domainObject.getVorname());
				pstmt.setString(2,domainObject.getName());
				if (domainObject.getGeburtsdatum() != null) {
					pstmt.setDate(3,DateConverter.convertJavaDateToSQLDateN(domainObject.getGeburtsdatum()));
				}
				if (domainObject.getTodesdatum() != null) {
					pstmt.setDate(4,DateConverter.convertJavaDateToSQLDateN(domainObject.getTodesdatum()));
				}
				pstmt.executeUpdate();
				
				rs = pstmt.getGeneratedKeys();
				if(rs != null && rs.next()){
					a = new AutorDAO().findById(rs.getInt(1));
				}
				
			}
	  catch (SQLException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
     } finally{
         try{
             if(rs != null) rs.close();
             if(pstmt != null) pstmt.close();
             if(conn != null) conn.close();
         } catch(Exception ex){}
     }
			
		return a;
	}

	@Override
	public Autor update(Autor domainObject) {
		ResultSet rs = null;
		Autor a = new Autor();
		String sql = "UPDATE autor SET "
				+ "vorname = ? "
				+ ",nachname = ? "
				+ (domainObject.getGeburtsdatum() != null ? ",geburtsdatum = ?":"")
				+ (domainObject.getTodesdatum() != null ? ",todesdatum = ? ":"")
				+ " WHERE id = ?";
			try {
				conn = dbConnection.getDBConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1,domainObject.getVorname());
				pstmt.setString(2,domainObject.getName());
				if (domainObject.getGeburtsdatum() != null) {
					pstmt.setDate(3,DateConverter.convertJavaDateToSQLDateN(domainObject.getGeburtsdatum()));
				}
				if (domainObject.getTodesdatum() != null) {
					pstmt.setDate(4,DateConverter.convertJavaDateToSQLDateN(domainObject.getTodesdatum()));
				}
				pstmt.setInt(5,  domainObject.getId());
				int i = pstmt.executeUpdate();
				if (i>0) {
					a = domainObject;
				}
				else {
					a = null;
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
			
		return a;
	}

	@Override
	public boolean delete(Autor domainObject) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Autor> getSelektion(Autor domainObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Autor findById(int id) {
		Autor a = new Autor();
		String sql = "SELECT "
				+ "id, "
				+ "nachname, "
				+ "vorname, "
				+ "geburtsdatum, "
				+ "todesdatum "
				+ "FROM autor "
				+ "WHERE id = ?";
			try {
				
				conn = dbConnection.getDBConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1,id);
				mRS = pstmt.executeQuery();
				while(mRS.next()) {
					 a.setId(mRS.getInt(1));
					 a.setName(mRS.getString(2));
					 a.setVorname(mRS.getString(3));
					 a.setGeburtsdatum(mRS.getDate(4));
					 a.setTodesdatum(mRS.getDate(5));
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
			
			return a;
	}
	

	@Override
	public List<Autor> findAll() {
		String sql = "SELECT "
				+ "id, "
				+ "nachname, "
				+ "vorname, "
				+ "geburtsdatum, "
				+ "todesdatum "
				+ "FROM autor";
			try {
				
				conn = dbConnection.getDBConnection();
				pstmt = conn.prepareStatement(sql);
				mRS = pstmt.executeQuery();
				while(mRS.next()) {
					 Autor a = new Autor();
					 a.setId(mRS.getInt(1));
					 a.setName(mRS.getString(2));
					 a.setVorname(mRS.getString(3));
					 a.setGeburtsdatum(mRS.getDate(4));
					 a.setTodesdatum(mRS.getDate(5));
					 autorListe.add(a);}
				
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
			
			return autorListe;
	}

}

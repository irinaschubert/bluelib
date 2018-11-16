package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import domain.Autor;
import hilfsklassen.DateConverter;
import hilfsklassen.SQLHelfer;
import interfaces.DAOInterface;

/*
 * Verwaltet die CRUD-Operationen für Autoren
 * @autor Schmutz
 * 
 */
public class AutorDAO implements DAOInterface<Autor> {
	
	private DBConnection dbConnection = null;
	private Connection conn = null; 
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
		int argCounter = 0;
		String sql = "INSERT INTO "
				+ "autor "
				+ "(vorname, "
				+ "nachname "
				+ (domainObject.getGeburtsdatum() != null ? ",geburtsdatum ":"")
				+ (domainObject.getTodesdatum() != null ? ",todesdatum ":"")
				+ ",geloescht "
				+ ") "
				+ "VALUES "
				+ "(?, ? "
				+ (domainObject.getGeburtsdatum() != null?",? ":"")
				+ (domainObject.getTodesdatum() != null?",? ":"")
				+ ", ?)" ;
			try {
				conn = dbConnection.getDBConnection();
				pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				argCounter++;
				pstmt.setString(argCounter,domainObject.getVorname());
				argCounter++;
				pstmt.setString(argCounter,domainObject.getName());
				if (domainObject.getGeburtsdatum() != null) {
					argCounter++;
					pstmt.setDate(argCounter,DateConverter.convertJavaDateToSQLDateN(domainObject.getGeburtsdatum()));
				}
				if (domainObject.getTodesdatum() != null) {
					argCounter++;
					pstmt.setDate(argCounter,DateConverter.convertJavaDateToSQLDateN(domainObject.getTodesdatum()));
				}
				argCounter++;
				pstmt.setBoolean(argCounter, domainObject.getGeloescht());
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
				+ ",geburtsdatum = ?"
				+ ",todesdatum = ? "
				+ ",geloescht = ?"
				+ " WHERE id = ?";
			try {
				conn = dbConnection.getDBConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1,domainObject.getVorname());
				pstmt.setString(2,domainObject.getName());
				
				if (domainObject.getGeburtsdatum() != null) {
				pstmt.setDate(3,DateConverter.convertJavaDateToSQLDateN(domainObject.getGeburtsdatum()));
				}
				else {
					pstmt.setNull(3,java.sql.Types.DATE);
				}
				
				if (domainObject.getTodesdatum() != null) {
				pstmt.setDate(4,DateConverter.convertJavaDateToSQLDateN(domainObject.getTodesdatum()));
				}
				else {
					pstmt.setNull(4,java.sql.Types.DATE);
				}
				pstmt.setBoolean(5, domainObject.getGeloescht());
				pstmt.setInt(6,  domainObject.getId());
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
			ResultSet rs = null;
			boolean geloescht = false;
			String sql = "DELETE FROM "
					+ "autor "
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
	public List<Autor> getSelektion(Autor domainObject) {
		ResultSet rs = null;
		int whereCounter =1;
		String sql = "SELECT "
				+ "id, "
				+ "nachname, "
				+ "vorname, "
				+ "geburtsdatum, "
				+ "todesdatum, "
				+ "geloescht "
				+ "FROM autor ";
				
				if (domainObject.getName() != null) {
					sql = sql + "WHERE nachname";
					sql = sql + (SQLHelfer.likePruefung(domainObject.getName())?" LIKE": " =");
					sql = sql + " ?";
					whereCounter++;
				}
				
				if (domainObject.getVorname() != null) {
					sql = sql + (whereCounter > 1?" AND": " WHERE");
					sql = sql + (" vorname");
					sql = sql + (SQLHelfer.likePruefung(domainObject.getVorname())?" LIKE": " =");
					sql = sql + " ?";
					whereCounter++;
				}
				
				if (domainObject.getGeburtsdatum() != null) {
					sql = sql + (whereCounter > 1?" AND": " WHERE");
					sql = sql + (" geburtsdatum = ?");
					whereCounter++;
				}
				if (domainObject.getTodesdatum() != null) {
					sql = sql + (whereCounter > 1?" AND": " WHERE");
					sql = sql + (" todesdatum = ?");
					whereCounter++;
				}
			
					sql = sql + (whereCounter > 1?" AND": " WHERE");
					sql = sql + (" geloescht = ?");
				
			try {
				
				int pCounter = 1;
				conn = dbConnection.getDBConnection();
				pstmt = conn.prepareStatement(sql);
				if (domainObject.getName() != null) {
					pstmt.setString(pCounter,SQLHelfer.SternFragezeichenErsatz(domainObject.getName()));
					pCounter++;
				}
				if (domainObject.getVorname() != null) {
					pstmt.setString(pCounter,SQLHelfer.SternFragezeichenErsatz(domainObject.getVorname()));
					pCounter++;
				}
				if (domainObject.getGeburtsdatum() != null) {
					pstmt.setDate(pCounter, DateConverter.convertJavaDateToSQLDateN(domainObject.getGeburtsdatum()));
					pCounter++;
				}
				if (domainObject.getTodesdatum() != null) {
					pstmt.setDate(pCounter, DateConverter.convertJavaDateToSQLDateN(domainObject.getTodesdatum()));
					pCounter++;
				}
				pstmt.setBoolean(pCounter, domainObject.getGeloescht());
				

				rs = pstmt.executeQuery();
				while(rs.next()) {
					 Autor a = new Autor();
					 a.setId(rs.getInt(1));
					 a.setName(rs.getString(2));
					 a.setVorname(rs.getString(3));
					 a.setGeburtsdatum(rs.getDate(4));
					 a.setTodesdatum(rs.getDate(5));
					 a.setGeloescht(rs.getBoolean(6));
					 autorListe.add(a);
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
			
			return autorListe;
		
	}

	@Override
	public Autor findById(int id) {
		Autor a = new Autor();
		ResultSet rs = null;
		String sql = "SELECT "
				+ "id, "
				+ "nachname, "
				+ "vorname, "
				+ "geburtsdatum, "
				+ "todesdatum, "
				+ "geloescht "
				+ "FROM autor "
				+ "WHERE id = ?";
			try {
				
				conn = dbConnection.getDBConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1,id);
				rs = pstmt.executeQuery();
				while(rs.next()) {
					 a.setId(rs.getInt(1));
					 a.setName(rs.getString(2));
					 a.setVorname(rs.getString(3));
					 a.setGeburtsdatum(rs.getDate(4));
					 a.setTodesdatum(rs.getDate(5));
					 a.setGeloescht(rs.getBoolean(6));
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
		ResultSet rs = null;
	
		String sql = "SELECT "
				+ "id, "
				+ "nachname, "
				+ "vorname, "
				+ "geburtsdatum, "
				+ "todesdatum, "
				+ "geloescht "
				+ "FROM autor "
				+ "ORDER BY nachname, vorname";
			try {
				
				conn = dbConnection.getDBConnection();
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				while(rs.next()) {
					 Autor a = new Autor();
					 a.setId(rs.getInt(1));
					 a.setName(rs.getString(2));
					 a.setVorname(rs.getString(3));
					 a.setGeburtsdatum(rs.getDate(4));
					 a.setTodesdatum(rs.getDate(5));
					 a.setGeloescht(rs.getBoolean(6));
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
	
	public List<Autor> findeAutorenZuMedium(int id){
		ResultSet rs = null;
		List<Autor> autorenListe = null;
		String sql = "SELECT "
				+ "ma.autor_id "
				+ "FROM mediumautor ma"
				+ "WHERE ma.medium_id = ?";
			try {
				
				conn = dbConnection.getDBConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1,id);
				rs = pstmt.executeQuery();
				AutorDAO autorDAO = new AutorDAO();
				while(rs.next()) {
					autorenListe.add(autorDAO.findById(rs.getInt(1)));					
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
			
			return autorListe;
				
				
			
	}

}

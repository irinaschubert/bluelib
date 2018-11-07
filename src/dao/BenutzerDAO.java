package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import domain.Benutzer;
import hilfsklassen.DateConverter;
import hilfsklassen.SQLHelfer;
import interfaces.DAOInterface;

/**
 * Verwaltet die CRUD-Operationen für Benutzer
 * @version 0.1 06.11.2018
 * @author irina
 *
 */
public class BenutzerDAO implements DAOInterface<Benutzer> {
	
	private DBConnection dbConnection;
	private Connection conn = null; 
	private PreparedStatement pstmt = null;
	private List<Benutzer> benutzerListe;
	
	public BenutzerDAO () {
		benutzerListe = new ArrayList<>();
		dbConnection = DBConnection.getInstance();
	}

	@Override
	public Benutzer save(Benutzer domainObject) {
		ResultSet rs = null;
		Benutzer a = new Benutzer();
		int argCounter = 0;
		String sql = "INSERT INTO "
				+ "person "
				+ "(vorname, "
				+ "nachname "
				+ (domainObject.getGeburtsdatum() != null ? ",Geburtstag ":"")
				+ (domainObject.getTelefon() != null ? ",telefon ":"")
				+ (domainObject.getAdresse() != null ? ",StrasseUndNr ":"")
				+ (domainObject.getBemerkung() != null ? ",bemerkung ":"")
				+ (domainObject.getEmail() != null ? ",email ":"")
				+ (domainObject.getErfassungDatum() != null ? ",erfassungsdatum ":"")
				+ (domainObject.getErfassungMitarbeiter() != null ? ",person_id ":"")
				+ (domainObject.getAnrede() != null ? ",anrede_id ":"")
				+ (domainObject.getAdresse() != null ? ",ort_id ":"")
				+ ",geloescht "
				+ ") "
				+ "VALUES "
				+ "(?, ? "
				+ (domainObject.getGeburtsdatum() != null?",? ":"")
				+ (domainObject.getTelefon() != null ? ",? ":"")
				+ (domainObject.getAdresse() != null ? ",? ":"")
				+ (domainObject.getBemerkung() != null ? ",? ":"")
				+ (domainObject.getEmail() != null ? ",? ":"")
				+ (domainObject.getErfassungDatum() != null ? ",? ":"")
				+ (domainObject.getErfassungMitarbeiter() != null ? ",? ":"")
				+ (domainObject.getAnrede() != null ? ",? ":"")
				+ (domainObject.getAdresse() != null ? ",? ":"")
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
				argCounter++;
				pstmt.setBoolean(argCounter, domainObject.getMitarbeiter());
				pstmt.executeUpdate();
				
				rs = pstmt.getGeneratedKeys();
				if(rs != null && rs.next()){
					a = new BenutzerDAO().findById(rs.getInt(1));
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
	public Benutzer update(Benutzer domainObject) {
		ResultSet rs = null;
		Benutzer a = new Benutzer();
		String sql = "UPDATE benutzer SET "
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
				pstmt.setBoolean(5, domainObject.getMitarbeiter());
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
	public boolean delete(Benutzer domainObject) {
		ResultSet rs = null;
		boolean geloescht = false;
		String sql = "DELETE FROM "
				+ "benutzer "
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
	public List<Benutzer> getSelektion(Benutzer domainObject) {
		ResultSet rs = null;
		int whereCounter =1;
		String sql = "SELECT "
				+ "id, "
				+ "nachname, "
				+ "vorname, "
				+ "geburtsdatum, "
				+ "todesdatum, "
				+ "geloescht "
				+ "FROM benutzer ";
				
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
				pstmt.setBoolean(pCounter, domainObject.getMitarbeiter());
				

				rs = pstmt.executeQuery();
				while(rs.next()) {
					 Benutzer a = new Benutzer();
					 a.setId(rs.getInt(1));
					 a.setName(rs.getString(2));
					 a.setVorname(rs.getString(3));
					 a.setGeburtsdatum(rs.getDate(4));
					 a.setMitarbeiter(rs.getBoolean(5));
					 benutzerListe.add(a);
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
			
			return benutzerListe;
		
	}

	@Override
	public Benutzer findById(int id) {
		Benutzer a = new Benutzer();
		ResultSet rs = null;
		String sql = "SELECT "
				+ "id, "
				+ "nachname, "
				+ "vorname, "
				+ "geburtsdatum, "
				+ "todesdatum, "
				+ "geloescht "
				+ "FROM benutzer "
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
					 a.setMitarbeiter(rs.getBoolean(5));
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
	public List<Benutzer> findAll() {
		ResultSet rs = null;
		
		String sql = "SELECT "
				+ "id, "
				+ "nachname, "
				+ "vorname, "
				+ "geburtsdatum, "
				+ "todesdatum, "
				+ "geloescht "
				+ "FROM benutzer";
			try {
				
				conn = dbConnection.getDBConnection();
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				while(rs.next()) {
					 Benutzer a = new Benutzer();
					 a.setId(rs.getInt(1));
					 a.setName(rs.getString(2));
					 a.setVorname(rs.getString(3));
					 a.setGeburtsdatum(rs.getDate(4));
					 a.setMitarbeiter(rs.getBoolean(5));
					 benutzerListe.add(a);}
				
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
			return benutzerListe;
	}
}

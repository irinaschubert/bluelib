package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import domain.Adresse;
import domain.Anrede;
import domain.Benutzer;
import domain.Ort;
import domain.Status;
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
		Benutzer benutzer = domainObject;
		
		Adresse adresse = benutzer.getAdresse();
		String strasseNr = adresse.getStrasse();
		Ort ort = adresse.getOrt();	
		String ortString = ort.getOrt();
		int plzInt = ort.getPlz();
		
		ResultSet rs = null;
		Benutzer a = new Benutzer();
		int argCounter = 0;
		
		String sql = "INSERT INTO "
				+ "person "
				+ "(vorname"
				+ ",nachname"
				+ ",anrede_id "
				+ ",strasseUndNr "
				//+ ",ort_id " // left join ort on person.ort_id = ort.id
				+ (domainObject.getBemerkung() != null ? ",bemerkung ":"")
				+ ",statusPers_id "
				+ (domainObject.getGeburtsdatum() != null ? ",geburtstag ":"")
				+ (domainObject.getTelefon() != null ? ",telefon ":"")
				+ (domainObject.getEmail() != null ? ",email ":"")
				+ (domainObject.getErfassungDatum() != null ? ",erfassungsdatum ":"")
				//+ (domainObject.getErfassungMitarbeiter() != null ? ",person_id ":"") // left join mitarbeiter on person.mitarbeiter_id = mitarbeiter.id
				//+ ",mitarbeiter_id " // left join mitarbeiter on person.mitarbeiter_id = mitarbeiter.id
				+ ") "
				+ "VALUES "
				+ "(?, ?, ?, ?"
				+ (domainObject.getBemerkung() != null ? ",? ":"")
				+ ",? "
				+ (domainObject.getGeburtsdatum() != null?",? ":"")
				+ (domainObject.getTelefon() != null ? ",? ":"")
				+ (domainObject.getEmail() != null ? ",? ":"")
				+ (domainObject.getErfassungDatum() != null ? ",? ":"")
				//+ (domainObject.getErfassungMitarbeiter() != null ? ",? ":"")
				//+ ",?)";
				+ ")";
		System.out.println(sql);
			try {
				conn = dbConnection.getDBConnection();
				pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				argCounter++;
				pstmt.setString(argCounter,domainObject.getVorname());
				argCounter++;
				pstmt.setString(argCounter,domainObject.getName());
				argCounter++;
				pstmt.setInt(argCounter,domainObject.getAnrede());
				argCounter++;
				pstmt.setString(argCounter,strasseNr);
				//argCounter++;
				//pstmt.setInt(argCounter,plzInt);
				//argCounter++;
				//pstmt.setString(argCounter,ortString); // argCounter = 6
				if (domainObject.getBemerkung() != null) {
					argCounter++;
					pstmt.setString(argCounter,domainObject.getBemerkung());
				}
				argCounter++;
				pstmt.setInt(argCounter,domainObject.getBenutzerStatus());
				if (domainObject.getGeburtsdatum() != null) {
					argCounter++;
					pstmt.setDate(argCounter,DateConverter.convertJavaDateToSQLDateN(domainObject.getGeburtsdatum()));
				}
				if (domainObject.getTelefon() != null) {
					argCounter++;
					pstmt.setString(argCounter,domainObject.getTelefon());
				}
				if (domainObject.getEmail() != null) {
					argCounter++;
					pstmt.setString(argCounter,domainObject.getEmail());
				}
				if (domainObject.getErfassungDatum() != null) {
					argCounter++;
					pstmt.setDate(argCounter,DateConverter.convertJavaDateToSQLDateN(domainObject.getErfassungDatum()));
				}
				/*if (domainObject.getErfassungMitarbeiter() != null) {
					argCounter++;
					String vorname = domainObject.getErfassungMitarbeiter().getVorname();
					String nachname = domainObject.getErfassungMitarbeiter().getName();
					String erfassungMA = new String(vorname + " " + nachname);
					pstmt.setString(argCounter,erfassungMA);
				}*/
				argCounter++;
				//pstmt.setInt(argCounter,domainObject.getMitarbeiterStatus());
				System.out.println(pstmt);
				
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
				pstmt.setInt(5, domainObject.getMitarbeiterStatus());
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
				+ "StrasseUndNr, "
				//+ "ort_id, "
				+ "statusPers_id "
				+ "FROM person ";
				
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
				
				if (domainObject.getAdresse() != null) {
					Benutzer benutzer = domainObject;
					Adresse adresse = benutzer.getAdresse();
					String strasseNr = adresse.getStrasse();
					Ort ort = adresse.getOrt();	
					String ortString = ort.getOrt();
					int plzInt = ort.getPlz();
					sql = sql + (whereCounter > 1?" AND": " WHERE");
					sql = sql + (" strasseUndNr");
					sql = sql + (SQLHelfer.likePruefung(strasseNr)?" LIKE": " =");
					sql = sql + " ?";
					whereCounter++;
				}
				// TODO Ort und PLZ
					sql = sql + (whereCounter > 1?" AND": " WHERE");
					sql = sql + (" statusPers_id = ?");
				
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
				if (domainObject.getAdresse() != null) {
					Benutzer benutzer = domainObject;
					Adresse adresse = benutzer.getAdresse();
					String strasseNr = adresse.getStrasse();
					Ort ort = adresse.getOrt();	
					String ortString = ort.getOrt();
					int plzInt = ort.getPlz();
					pstmt.setString(pCounter,SQLHelfer.SternFragezeichenErsatz(strasseNr));
					pCounter++;
				}
				// TODO Ort und PLZ
				pstmt.setInt(pCounter, domainObject.getBenutzerStatus());

				rs = pstmt.executeQuery();
				while(rs.next()) {
					Benutzer a = new Benutzer();
					 
					
					a.setId(rs.getInt(1));
					a.setName(rs.getString(2));
					a.setVorname(rs.getString(3));
					if(a.getAdresse() != null) {
						Adresse adresse = a.getAdresse();
						String strasseNr = adresse.getStrasse();
						Ort ort = adresse.getOrt();	
						String ortString = ort.getOrt();
						int plzInt = ort.getPlz();
						a.setAdresse(new Adresse(rs.getString(4), new Ort(1234,"Dummyort")));
					}
					a.setBenutzerStatus(rs.getInt(5));
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
				+ "vorname "
				+ "FROM person "
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
				+ "vorname "
				+ "FROM person";
			try {
				
				conn = dbConnection.getDBConnection();
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				while(rs.next()) {
					 Benutzer a = new Benutzer();
					 a.setId(rs.getInt(1));
					 a.setName(rs.getString(2));
					 a.setVorname(rs.getString(3));
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

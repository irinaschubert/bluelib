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
		ResultSet rs = null;
		Benutzer b = new Benutzer();
		int argCounter = 0;
		
		Adresse adresse = domainObject.getAdresse();
		String strasseNr = adresse.getStrasse();
		Ort ort = adresse.getOrt();	
		int ortId = ort.getId();
		
		String sql = "INSERT INTO "
				+ "person "
				+ "(vorname"
				+ ",nachname"
				+ ",anrede_id "
				+ ",strasseUndNr "
				+ ",ort_id "
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
				+ "(?, ?, ?, ?, ?"
				+ (domainObject.getBemerkung() != null ? ",? ":"")
				+ ",? "
				+ (domainObject.getGeburtsdatum() != null?",? ":"")
				+ (domainObject.getTelefon() != null ? ",? ":"")
				+ (domainObject.getEmail() != null ? ",? ":"")
				+ (domainObject.getErfassungDatum() != null ? ",? ":"")
				//+ (domainObject.getErfassungMitarbeiter() != null ? ",? ":"")
				//+ ",?)";
				+ ")";
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
				argCounter++;
				pstmt.setInt(argCounter,ortId);
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
				
				pstmt.executeUpdate();
				
				rs = pstmt.getGeneratedKeys();
				if(rs != null && rs.next()){
					b = new BenutzerDAO().findById(rs.getInt(1));
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
	return b;
	}
	

	@Override
	public Benutzer update(Benutzer domainObject) {
		Adresse adresse = domainObject.getAdresse();
		String strasseNr = adresse.getStrasse();
		Ort ort = adresse.getOrt();	
		int ortId = ort.getId();
		ResultSet rs = null;
		Benutzer a = new Benutzer();
		String sql = "UPDATE person SET "
				+ "vorname = ? "
				+ ",nachname = ? "
				+ ",anrede_id = ? "
				+ ",strasseUndNr = ? "
				+ ",ort_id = ? "
				+ ",bemerkung = ? "
				+ ",statusPers_id = ? "
				+ ",geburtstag = ? "
				+ ",telefon = ? "
				+ ",email = ? "
				+ ",erfassungsdatum = ? "
				+ " WHERE id = ?";
				//+ (domainObject.getErfassungMitarbeiter() != null ? ",person_id ":"") // left join mitarbeiter on person.mitarbeiter_id = mitarbeiter.id
				//+ ",mitarbeiter_id " // left join mitarbeiter on person.mitarbeiter_id = mitarbeiter.id
				
			try {
				conn = dbConnection.getDBConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1,domainObject.getVorname());
				pstmt.setString(2,domainObject.getName());
				pstmt.setInt(3,domainObject.getAnrede());
				pstmt.setString(4,strasseNr);
				pstmt.setInt(5,ortId);
				if(domainObject.getBemerkung() != null) {
					pstmt.setString(6,domainObject.getBemerkung());
				}
				else {
					pstmt.setString(6,"");
				}
				pstmt.setInt(7,domainObject.getBenutzerStatus());
				if (domainObject.getGeburtsdatum() != null) {
					pstmt.setDate(8,DateConverter.convertJavaDateToSQLDateN(domainObject.getGeburtsdatum()));
				}
				else {
					pstmt.setNull(8,java.sql.Types.DATE);
				}
				if (domainObject.getTelefon() != null) {
					pstmt.setString(9, domainObject.getTelefon());
				}
				else {
					pstmt.setString(9,"");
				}
				if (domainObject.getEmail() != null) {
					pstmt.setString(10,  domainObject.getEmail());
				}
				else {
					pstmt.setString(10,"");
				}
				if (domainObject.getErfassungDatum() != null) {
					pstmt.setDate(11,DateConverter.convertJavaDateToSQLDateN(domainObject.getErfassungDatum()));
				}
				else {
					pstmt.setNull(11,java.sql.Types.DATE);
				}
				// TODO ErfassungMA
				pstmt.setInt(12,  domainObject.getId());
				
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
				+ "person "
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
				+ "ort_id, "
				+ "statusPers_id "
				+ "FROM person ";
				if (domainObject.getId() != 0) {
					sql = sql + "WHERE id = ?";
					whereCounter++;
				}
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
					Adresse adresse = domainObject.getAdresse();
					
					if(adresse.getStrasse() != null || !adresse.getStrasse().equals("") || adresse.getStrasse() != "") {
						String strasseNr = adresse.getStrasse();
						sql = sql + (whereCounter > 1?" AND": " WHERE");
						sql = sql + (" strasseUndNr");
						sql = sql + (SQLHelfer.likePruefung(strasseNr)?" LIKE": " =");
						sql = sql + " ?";
						whereCounter++;
					}
					if(adresse.getOrt() != null) {
						sql = sql + (whereCounter > 1?" AND": " WHERE");
						sql = sql + (" ort_id = ?");
						whereCounter++;
					}
				}
				if (domainObject.getBenutzerStatus() != 0) {
					sql = sql + (whereCounter > 1?" AND": " WHERE");
					sql = sql + (" statusPers_id = ?");
				}
				
			try {
				int pCounter = 1;
				conn = dbConnection.getDBConnection();
				pstmt = conn.prepareStatement(sql);
				if (domainObject.getId() != 0) {
					pstmt.setInt(pCounter,domainObject.getId());
					pCounter++;
				}
				if (domainObject.getName() != null) {
					pstmt.setString(pCounter,SQLHelfer.SternFragezeichenErsatz(domainObject.getName()));
					pCounter++;
				}
				if (domainObject.getVorname() != null) {
					pstmt.setString(pCounter,SQLHelfer.SternFragezeichenErsatz(domainObject.getVorname()));
					pCounter++;
				}
				if (domainObject.getAdresse() != null) {
					Adresse adresse = domainObject.getAdresse();
					if(adresse.getStrasse() != null || !adresse.getStrasse().equals("") || adresse.getStrasse() != "") {
						String strasseNr = adresse.getStrasse();
						pstmt.setString(pCounter,SQLHelfer.SternFragezeichenErsatz(strasseNr));
						pCounter++;
					}
					if(adresse.getOrt() != null) {
						Ort ort = adresse.getOrt();
						int ortId = ort.getId();
						pstmt.setInt(pCounter,ortId);
						pCounter++;
					}
				}
				if (domainObject.getBenutzerStatus() != 0) {
					pstmt.setInt(pCounter, domainObject.getBenutzerStatus());
				}
				
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					Benutzer b = new Benutzer();
					b = findById(rs.getInt(1));				
					benutzerListe.add(b);
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
		OrtDAO ortDao = new OrtDAO();
		StatusDAO statusDAO = new StatusDAO();
		ResultSet rs = null;
		String sql = "SELECT "
				+ "id, vorname, nachname, strasseUndNr, ort_id, geburtstag, "
				+ "telefon, email, bemerkung, erfassungsdatum, person_id, anrede_id, statusPers_id "
				+ "FROM person "
				+ "WHERE id = ?";
			try {
				conn = dbConnection.getDBConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1,id);
				rs = pstmt.executeQuery();
				while(rs.next()) {
					 a.setId(rs.getInt(1));
					 a.setVorname(rs.getString(2));
					 a.setName(rs.getString(3));
					 Ort ort = ortDao.findById(rs.getInt(5));
					 int plz = ort.getPlz();
					 String ortString = ort.getOrt();
					 a.setAdresse(new Adresse(rs.getString(4), new Ort(rs.getInt(5), plz, ortString)));
					 a.setGeburtsdatum(rs.getDate(6));
					 a.setTelefon(rs.getString(7));
					 a.setEmail(rs.getString(8));
					 a.setBemerkung(rs.getString(9));
					 a.setErfassungDatum(rs.getDate(10));
					 //a.setErfassungMitarbeiter(rs.getInt(11));
					 a.setAnrede(rs.getInt(12));
					 Status status = statusDAO.findById(rs.getInt(13));
					 a.setBenutzerStatus(status.getId());
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

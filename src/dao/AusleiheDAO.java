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
import domain.Ausleihe;
import domain.Autor;
import domain.Benutzer;
import domain.Ort;
import domain.Status;
import domain.Verlag;
import hilfsklassen.DateConverter;
import hilfsklassen.SQLHelfer;
import interfaces.DAOInterface;
/**
 * @version 0.1 28.10.2018
 * @author Mike
 *
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
		String sql = "INSERT INTO "
				+ "ausleihe "
				+ "(id,"
				+ " person_id,"
				+ " medium_id"
				+ (domainObject.getAusleiheDatum() != null ? ", von":"")
				+ (domainObject.getRueckgabeDatum() != null ? ", retour":"")
				+ (domainObject.getAusleiheMitarbeiterID() > 0 ? ", erfasser_person_id":"")
				+ ", retour_person_id"
				+ ") "
				+ "VALUES "
				+ "(?, ?, ?"
				+ (domainObject.getAusleiheDatum() != null?",? ":"")
				+ (domainObject.getRueckgabeDatum() != null?",? ":"")
				+ (domainObject.getAusleiheMitarbeiterID() >0 ?",? ":"")
				+ ", ?"
				+ ")";
			try {
				conn = dbConnection.getDBConnection();
				pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				argCounter++;
				pstmt.setInt(argCounter,domainObject.getId());
				argCounter++;
				pstmt.setInt(argCounter,domainObject.getBenutzerID());
				argCounter++;
				pstmt.setInt(argCounter,domainObject.getMediumID());
				if (domainObject.getAusleiheDatum() != null) {
					argCounter++;
					pstmt.setDate(argCounter,DateConverter.convertJavaDateToSQLDateN(domainObject.getAusleiheDatum()));
				}
				if (domainObject.getRueckgabeDatum() != null) {
					argCounter++;
					pstmt.setDate(argCounter,DateConverter.convertJavaDateToSQLDateN(domainObject.getRueckgabeDatum()));
				}
				if (domainObject.getAusleiheMitarbeiterID() > 0) {
					argCounter++;
					pstmt.setInt(argCounter,domainObject.getAusleiheMitarbeiterID());
				}
				if (domainObject.getRueckgabeMitarbeiterID() > 0) {
					argCounter++;
					pstmt.setInt(argCounter,domainObject.getRueckgabeMitarbeiterID());
				}else {
					argCounter++;
					pstmt.setObject(argCounter,1); // muss null sein, sobald in DB angepasst
				}
				pstmt.executeUpdate();
				
				rs = pstmt.getGeneratedKeys();
				if(rs != null && rs.next()){
					a = new AusleiheDAO().findById(rs.getInt(1));
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
	public Ausleihe update(Ausleihe domainObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(Ausleihe domainObject) {
		// TODO Auto-generated method stub
		return false;
	}

	
	public List<Ausleihe> getSelektionByBenutzer(Benutzer domainObject) {
		ResultSet rs = null;	
		String sql = "SELECT "
				+ "id, "
				+ "medium_id, "
				+ "von "
				+ "FROM ausleihe ";
				sql = sql + ("where person_id = ?");
				
			try {
				int pCounter = 1;
				conn = dbConnection.getDBConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(pCounter,domainObject.getId());
				
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					Ausleihe a = new Ausleihe();
					a = findById(rs.getInt(1));				
					ausleiheListe.add(a);
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
			return ausleiheListe;
	}

	@Override
	public Ausleihe findById(int id) {
		Ausleihe a = new Ausleihe();
		ResultSet rs = null;
		String sql = "SELECT "
				+ "id, person_id, medium_id, von, retour, erfasser_person_id, retour_person_id "
				+ "FROM ausleihe "
				+ "WHERE id = ?";
			try {
				conn = dbConnection.getDBConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1,id);
				rs = pstmt.executeQuery();
				while(rs.next()) {
					 a.setId(rs.getInt(1));
					 a.setBenutzerID(rs.getInt(2));
					 a.setMediumID(rs.getInt(3));
					 a.setAusleiheDatum(rs.getDate(4));
					 if(rs.getDate(5) != null) {
						 a.setRueckgabeDatum(rs.getDate(5));
					 }else {
						 a.setRueckgabeDatum(null);
					 }
					 a.setAusleiheMitarbeiterID(rs.getInt(6));
					 a.setRueckgabeMitarbeiterID(rs.getInt(7));
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
	public ArrayList<Ausleihe> findAll() {
		ArrayList<Ausleihe> ausleihen = new ArrayList<>();
		ResultSet rs = null;	
		String sql = "SELECT id, person_id, medium_id, von, retour from ausleihe";
			try {
				conn = dbConnection.getDBConnection();
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				while(rs.next()) {
					Ausleihe a = new Ausleihe();
					a = findById(rs.getInt(1));				
					ausleihen.add(a);
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
			return ausleihen;
	}

	@Override
	public List<Ausleihe> getSelektion(Ausleihe domainObject) {
		// TODO Auto-generated method stub
		return null;
	}


}

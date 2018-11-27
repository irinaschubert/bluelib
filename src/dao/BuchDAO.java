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
import domain.Buch;
import domain.Medium;
import hilfsklassen.DateConverter;
import hilfsklassen.SQLHelfer;
import interfaces.DAOInterface;
/**
 * @version 0.1 28.10.2018
 * @author Mike
 *
 */
public class BuchDAO implements DAOInterface<Buch> {
	private DBConnection dbConnection = null;
	private Connection conn = null; 
	private PreparedStatement pstmt = null;
	private List<Buch> buchListe = null;
	
	public BuchDAO() {
		buchListe = new ArrayList<>();
		dbConnection = DBConnection.getInstance();
	}
	
	@Override
	public Buch save(Buch domainObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Buch update(Buch domainObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(Buch domainObject) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Buch> getSelektion(Buch domainObject) {
		ResultSet rs = null;
		String sql = "SELECT "
				+ "m.id, "
				+ "m.titel, "
				+ "m.barcode, "
				+ "m.preis, "
				+ "m.erscheinungsjahr, "
				+ "m.reihe, "
				+ "m.erscheinungsort, "
				+ "m.erfassungsdatum, "
				+ "m.bemerkung, "
				+ "m.signatur, "
				+ "m.verlag_id, "
				+ "m.statusMedi_id, "
				+ "b.seiten, "
				+ "m.person_id, "
				+ "b.auflage "
				+ "FROM medium m "
				+ "INNER JOIN buch b on b.medium_id = m.id ";
				
				
				Boolean whereUsed = false;
				
				if (domainObject.getStatus() != null) {
					sql = sql + " WHERE m.statusMedi_id = ? ";
					whereUsed = true;
						}
				
				if (domainObject.getBarcodeNr() >= 0) {
					sql = sql + (whereUsed?"AND ": "WHERE ");
					whereUsed = true;
					sql = sql + "m.barcode = ? ";
						}
				
				if (domainObject.getId() > -1) {
					sql = sql + (whereUsed?"AND ": "WHERE ");
					whereUsed = true;
					sql = sql + "m.id = ? ";
						}
				
				if (domainObject.getTitel() != null) {
					sql = sql + (whereUsed?"AND ": "WHERE ");
					whereUsed = true;
					sql = sql + "titel ";
					sql = sql + (SQLHelfer.likePruefung(domainObject.getTitel())?" LIKE": " =");
					sql = sql + " ? ";
				}
				
				if (domainObject.getAutoren() != null) {
					if (domainObject.getAutoren().size() > 0) {
						sql = sql + (whereUsed?"AND ": "WHERE ");
						whereUsed = true;
						sql = sql + "m.id IN (SELECT ma.medium_id FROM mediumautor ma "
								+ "WHERE ma.autor_id IN (";
					for (int i = 0; i < domainObject.getAutoren().size();i++) {
						sql = sql + (i + 1 == domainObject.getAutoren().size()?"? ))":"?, ");
					}
					}
				}
					
				if (domainObject.getVerlag() != null) {
					sql = sql + (whereUsed?"AND ": "WHERE ");
					whereUsed = true;
					sql = sql + "m.verlag_id = ? ";
				}
				if (domainObject.getSignatur() != null) {
					sql = sql + (whereUsed?"AND ": "WHERE ");
					whereUsed = true;
					sql = sql + "m.verlag_id ";
					sql = sql + (SQLHelfer.likePruefung(domainObject.getSignatur())?" LIKE": " =");
				}
				
			try {
				
				int pCounter = 1;
				conn = dbConnection.getDBConnection();
				pstmt = conn.prepareStatement(sql);
				if (domainObject.getStatus() != null) {
					pstmt.setInt(pCounter++,domainObject.getStatus().getId());
				}
				if (domainObject.getBarcodeNr() >= 0) {
					pstmt.setInt(pCounter++,domainObject.getBarcodeNr());
				}
				if (domainObject.getId() > -1) {
					pstmt.setInt(pCounter++,domainObject.getId());
				}
				if (domainObject.getTitel() != null) {
					pstmt.setString(pCounter++, SQLHelfer.SternFragezeichenErsatz(domainObject.getTitel()));
				}
				if (domainObject.getAutoren() != null) {
					if (domainObject.getAutoren().size() > 0) {
						for (Autor a : domainObject.getAutoren()) {
							pstmt.setInt(pCounter++, a.getId());
						}
					}
				}
				
				if (domainObject.getVerlag() != null) {
					pstmt.setInt(pCounter++,domainObject.getVerlag().getId());
				}
				if (domainObject.getSignatur() != null) {
					pstmt.setString(pCounter++,domainObject.getSignatur());
				}
				

				rs = pstmt.executeQuery();
				AutorDAO autorDAO = new AutorDAO();
				while(rs.next()) {
					int count = 1;
					 Buch b = new Buch();
					 b.setId(rs.getInt(count++));
					 b.setTitel(rs.getString(count++));
					 b.setBarcodeNr(rs.getInt(count++));
					 b.setPreis(rs.getDouble(count++));
					 b.setErscheinungsJahr(rs.getString(count++));
					 b.setReihe(rs.getString(count++));
					 b.setErscheinungsOrt(rs.getString(count++));
					 b.setErfassungDatum(rs.getDate(count++));
					 b.setBemerkung(rs.getString(count++));
					 b.setSignatur(rs.getString(count++));
					 b.setVerlag(new VerlagDAO().findById(rs.getInt(count++)));
					 b.setStatus(new StatusDAO().findById(rs.getInt(count++)));
					 b.setAnzahlSeiten(rs.getInt(count++));
					 b.setErfasserId(rs.getInt(count++));
					 b.setErfasserName(new MitarbeiterDAO().findNameVornameById(b.getErfasserId()));
					 b.setAuflage(rs.getString(count++));
					 b.setAutoren(new AutorDAO().findeAutorenZuMedium(b.getId()));
					 b.setSchlagwoerter(new SchlagwortDAO().findeSchlagwoerterZuMedium(b.getId()));
					 buchListe.add(b);
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
				
			
			return buchListe;
	}

	@Override
	public Buch findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public Buch findByBarcode(String barcode) {
		ResultSet rs = null;
		Buch b = new Buch();
		String sql = "SELECT "
				+ "m.id, "
				+ "m.titel, "
				+ "m.preis, "
				+ "m.erscheinungsjahr, "
				+ "m.reihe, "
				+ "m.erscheinungsort, "
				+ "m.erfassungsdatum, "
				+ "m.bemerkung, "
				+ "m.signatur, "
				+ "m.verlag_id, "
				+ "m.statusMedi_id, "
				+ "b.seiten, "
				+ "m.person_id, "
				+ "b.auflage "
				+ "FROM medium m "
				+ "INNER JOIN buch b on b.medium_id = m.id ";
				
				sql = sql + "WHERE m.barcode = ? ";
				
			try {
				int pCounter = 1;
				conn = dbConnection.getDBConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(pCounter,barcode);
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					int count = 1;
					 b.setId(rs.getInt(count++));
					 b.setTitel(rs.getString(count++));
					 b.setPreis(rs.getDouble(count++));
					 b.setErscheinungsJahr(rs.getString(count++));
					 b.setReihe(rs.getString(count++));
					 b.setErscheinungsOrt(rs.getString(count++));
					 b.setErfassungDatum(rs.getDate(count++));
					 b.setBemerkung(rs.getString(count++));
					 b.setSignatur(rs.getString(count++));
					 b.setVerlag(new VerlagDAO().findById(rs.getInt(count++)));
					 b.setStatus(new StatusDAO().findById(rs.getInt(count++)));
					 b.setAnzahlSeiten(rs.getInt(count++));
					 b.setErfasserId(rs.getInt(count++));
					 b.setErfasserName(new MitarbeiterDAO().findNameVornameById(b.getErfasserId()));
					 b.setAuflage(rs.getString(count++));
					 b.setAutoren(new AutorDAO().findeAutorenZuMedium(b.getId()));
					 b.setSchlagwoerter(new SchlagwortDAO().findeSchlagwoerterZuMedium(b.getId()));
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
			return b;
	}


	@Override
	public List<Buch> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
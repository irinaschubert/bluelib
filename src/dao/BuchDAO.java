package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import domain.Autor;
import domain.Buch;
import domain.Schlagwort;
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
		ResultSet rs = null;
		int medium_id = -1;
		int buch_id = 1;
		Buch b = null;
		String sql = "INSERT INTO "
				+ "medium "
				+ "(titel "
				+ ",barcode "
				+ ",erscheinungsjahr "
				+ ",erfassungsdatum "
				+ ",signatur "
				+ ",verlag_id "
				+ ",statusMedi_id "
				+ ",person_id "
				+ (domainObject.getPreis() != null ? ",preis ":"")
				+ (domainObject.getReihe() != null ? ",reihe ":"")
				+ (domainObject.getErscheinungsOrt() != null ? ",erscheinungsort ":"")
				+ (domainObject.getBemerkung() != null ? ",bemerkung ":"")
				+ ") "
				+ "VALUES "
				+ "(?, ? , ?, ?, ?, ?, ?, ? "
				+ (domainObject.getPreis() != null ? ",? " : "")
				+ (domainObject.getReihe() != null ? ",? " : "")
				+ (domainObject.getErscheinungsOrt() != null ? ",? " : "")
				+ (domainObject.getBemerkung() != null ? ",? " : "")
				+")" ;
		try {
			conn = dbConnection.getDBConnection();
			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			int argCounter = 1;
			pstmt.setString(argCounter++,domainObject.getTitel());
			pstmt.setInt(argCounter++,domainObject.getBarcodeNr());
			pstmt.setInt(argCounter++,domainObject.getErscheinungsJahr());
			pstmt.setDate(argCounter++,DateConverter.convertJavaDateToSQLDateN(domainObject.getErfassungDatum()));
			pstmt.setString(argCounter++,domainObject.getSignatur());
			pstmt.setInt(argCounter++,domainObject.getVerlag().getId());
			pstmt.setInt(argCounter++,domainObject.getStatus().getId());
			pstmt.setInt(argCounter++,domainObject.getErfasserId());
			if (domainObject.getPreis() != null) {
				pstmt.setBigDecimal(argCounter++,domainObject.getPreis());
			}
			if (domainObject.getReihe() != null) {
				pstmt.setString(argCounter++,domainObject.getReihe());
			}
			if (domainObject.getErscheinungsOrt() != null) {
				pstmt.setString(argCounter++,domainObject.getErscheinungsOrt());
			}
			if (domainObject.getBemerkung() != null) {
				pstmt.setString(argCounter++,domainObject.getBemerkung());
			}

			pstmt.executeUpdate();

			rs = pstmt.getGeneratedKeys();
			if(rs != null && rs.next()){
				medium_id = rs.getInt(1);
			}

			if (medium_id > -1) {

				sql = "INSERT INTO "
						+ "buch "
						+ "(seiten "
						+ ", isbn"
						+ ", auflage"
						+ ", medium_id"
						+ ") "
						+ "VAlUES "
						+ "(?, ?, ?, ?)";
				pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				argCounter = 1;
				pstmt.setInt(argCounter++,domainObject.getAnzahlSeiten());
				pstmt.setLong(argCounter++,domainObject.getIsbn());
				pstmt.setString(argCounter++,domainObject.getAuflage());
				pstmt.setInt(argCounter++,medium_id);
				pstmt.executeUpdate();

				rs = pstmt.getGeneratedKeys();
				if(rs != null && rs.next()){
					buch_id = rs.getInt(1);
				}
				
				for (Autor a : domainObject.getAutoren()) {

					sql = "INSERT INTO "
							+ "autorbuch "
							+ "(buch_id "
							+ ",autor_id "
							+ ") "
							+ "VALUES "
							+ "(?, ?)";
					pstmt = conn.prepareStatement(sql);
					argCounter = 1;
					pstmt.setInt(argCounter++,buch_id);
					pstmt.setInt(argCounter++,a.getId());
					pstmt.executeUpdate();
				}

				for (Schlagwort s : domainObject.getSchlagwoerter()) {

					sql = "INSERT INTO "
							+ "mediumschlagwort "
							+ "(medium_id "
							+ ",schlagwort_id "
							+ ") "
							+ "VALUES "
							+ "(?, ?)";
					pstmt = conn.prepareStatement(sql);
					argCounter = 1;
					pstmt.setInt(argCounter++,medium_id);
					pstmt.setInt(argCounter++,s.getId());
					pstmt.executeUpdate();
				}

				b = new BuchDAO().findById(medium_id);


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
	public Buch update(Buch domainObject) {
		ResultSet rs = null;
		Buch b = null;
		String sql = "UPDATE medium SET "
				+ "titel = ? "
				+ ",barcode = ? "
				+ ",erscheinungsjahr = ?"
				+ ",signatur = ? "
				+ ",verlag_id = ?"
				+ ",statusMedi_id = ? "
				+ ",preis = ? "
				+ ",reihe = ? "
				+ ",erscheinungsort = ?"
				+ ",bemerkung = ? "
				+ "WHERE id = ? ";

		try {
			conn = dbConnection.getDBConnection();
			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			int argCounter = 1;
			pstmt.setString(argCounter++,domainObject.getTitel());
			pstmt.setInt(argCounter++,domainObject.getBarcodeNr());
			pstmt.setInt(argCounter++,domainObject.getErscheinungsJahr());
			pstmt.setString(argCounter++,domainObject.getSignatur());
			pstmt.setInt(argCounter++,domainObject.getVerlag().getId());
			pstmt.setInt(argCounter++,domainObject.getStatus().getId());
			if (domainObject.getPreis() != null) {
				pstmt.setBigDecimal(argCounter++,domainObject.getPreis());
			}
			else {
				pstmt.setNull(argCounter++, java.sql.Types.INTEGER);
			}
			if (domainObject.getReihe() != null) {
				pstmt.setString(argCounter++,domainObject.getReihe());
			}
			else
			{
				pstmt.setNull(argCounter++,java.sql.Types.VARCHAR);
			}
			if (domainObject.getErscheinungsOrt() != null) {
				pstmt.setString(argCounter++,domainObject.getErscheinungsOrt());
			}
			else
			{
				pstmt.setNull(argCounter++,java.sql.Types.VARCHAR);
			}
			if (domainObject.getBemerkung() != null) {
				pstmt.setString(argCounter++,domainObject.getBemerkung());
			}
			else
			{
				pstmt.setNull(argCounter++,java.sql.Types.VARCHAR);
			}
			pstmt.setInt(argCounter++, domainObject.getId());

			pstmt.executeUpdate();

			sql = "UPDATE buch SET "
					+ "seiten = ? "
					+ ", isbn = ? "
					+ ", auflage = ? "
					+ "WHERE medium_id = ?";

			pstmt = conn.prepareStatement(sql);
			argCounter = 1;
			pstmt.setInt(argCounter++,domainObject.getAnzahlSeiten());
			pstmt.setLong(argCounter++,domainObject.getIsbn());
			pstmt.setString(argCounter++,domainObject.getAuflage());
			pstmt.setInt(argCounter++,domainObject.getId());
			pstmt.executeUpdate();

			sql = "SELECT "
					+ "autor_id "
					+ "FROM autorbuch "
					+ "WHERE buch_id = ? ";

			pstmt = conn.prepareStatement(sql);
			argCounter = 1;
			pstmt.setInt(argCounter++,domainObject.getId());
			rs = pstmt.executeQuery();

			List<Integer> autorenListe = new ArrayList<>(); 
			while(rs.next()) {
				autorenListe.add(rs.getInt(1));
			}


			// Nicht in DB vorhandene Autoren hinzufügen
			for (Autor a : domainObject.getAutoren()) {
				Boolean enthalten = false;
				for  (int i : autorenListe) {				
					if (a.getId() == i) {
						enthalten = true;
					}
				
				}	
				
				if (enthalten == false) {
					sql = "INSERT INTO autorbuch "
							+ "(buch_id, "
							+ "autor_id) "
							+ "VALUES "
							+ "( ?, ?)";
					pstmt = conn.prepareStatement(sql);
					argCounter = 1;
					pstmt.setInt(argCounter++,domainObject.getId());
					pstmt.setInt(argCounter++,a.getId());
					pstmt.executeUpdate();								
				}

			}

			// Nicht in Liste vorhandene Autoren in DB löschen
			for  (int i : autorenListe) {				
				Boolean enthalten = false;
				for (Autor a : domainObject.getAutoren()) {

					if (a.getId() == i) {
						enthalten = true;
					}


				}	
				if (enthalten == false) {
					sql = "DELETE FROM autorbuch "
							+ "WHERE buch_id = ? "
							+ "AND autor_id = ?";
					pstmt = conn.prepareStatement(sql);
					argCounter = 1;
					pstmt.setInt(argCounter++,domainObject.getId());
					pstmt.setInt(argCounter++,i);
					pstmt.executeUpdate();								
				}

			}


			sql = "SELECT "
					+ "schlagwort_id "
					+ "FROM mediumschlagwort "
					+ "WHERE medium_id = ? ";

			pstmt = conn.prepareStatement(sql);
			argCounter = 1;
			pstmt.setInt(argCounter++,domainObject.getId());
			rs = pstmt.executeQuery();


			List<Integer> schlagwortListe = new ArrayList<>(); 
			while(rs.next()) {
				schlagwortListe.add(rs.getInt(1));
			}

			// Nicht in DB vorhandene Schlagworte hinzufügen
			for (Schlagwort s : domainObject.getSchlagwoerter()) {
				Boolean enthalten = false;
				for  (int i : schlagwortListe) {				
					if (s.getId() == i) {
						enthalten = true;
					}
					
					if (enthalten == false) {
						sql = "INSERT INTO mediumschlagwort "
								+ "(medium_id, "
								+ "schlagwort_id) "
								+ "VALUES "
								+ "( ?, ?)";
						pstmt = conn.prepareStatement(sql);
						argCounter = 1;
						pstmt.setInt(argCounter++,domainObject.getId());
						pstmt.setInt(argCounter++,s.getId());
						pstmt.executeUpdate();								
					}
				}	

			}

			// Nicht in Liste vorhandene Schlagworte in DB löschen
			for  (int i : schlagwortListe) {				
				Boolean enthalten = false;
				for (Schlagwort s : domainObject.getSchlagwoerter()) {

					if (s.getId() == i) {
						enthalten = true;
					}


				}	

				if (enthalten == false) {
					sql = "DELETE FROM mediumschlagwort "
							+ "WHERE medium_id = ? "
							+ "AND schlagwort_id = ?";
					pstmt = conn.prepareStatement(sql);
					argCounter = 1;
					pstmt.setInt(argCounter++,domainObject.getId());
					pstmt.setInt(argCounter++,i);
					pstmt.executeUpdate();								
				}

			}

			b = domainObject;			


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
				+ "b.id, "
				+ "b.auflage, "
				+ "b.isbn "
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
				sql = sql + "m.id IN (SELECT ab.buch_id FROM autorbuch ab "
						+ "WHERE ab.autor_id IN (";
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
				b.setPreis(rs.getBigDecimal(count++));
				b.setErscheinungsJahr(rs.getInt(count++));
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
				b.setBuchId(rs.getInt(count++));
				b.setAuflage(rs.getString(count++));
				b.setIsbn(rs.getLong(count++));
				b.setAutoren(new AutorDAO().findeAutorZuBuch(b.getBuchId()));
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
		ResultSet rs = null;
		Buch b = new Buch();
		String sql = "SELECT "
				+ "m.id, "
				+ "m.titel, "
				+ "m.barcode, "
				+ "m.preis, "
				//				+ "m.erscheinungsjahr, "
				+ "m.reihe, "
				+ "m.erscheinungsort, "
				+ "m.erfassungsdatum, "
				+ "m.bemerkung, "
				+ "m.signatur, "
				+ "m.verlag_id, "
				+ "m.statusMedi_id, "
				+ "b.seiten, "
				+ "m.person_id, "
				+ "b.id, "
				+ "b.auflage "
				+ "FROM medium m "
				+ "INNER JOIN buch b on b.medium_id = m.id ";

		sql = sql + "WHERE m.id = ? ";

		try {
			int pCounter = 1;
			conn = dbConnection.getDBConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(pCounter,id);
			rs = pstmt.executeQuery();

			while(rs.next()) {
				int count = 1;
				b.setId(rs.getInt(count++));
				b.setTitel(rs.getString(count++));
				b.setBarcode(rs.getString(count++));
				b.setPreis(rs.getBigDecimal(count++));
				//					b.setErscheinungsJahr(rs.getString(count++));
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
				b.setBuchId(rs.getInt(count++));
				b.setAuflage(rs.getString(count++));
				b.setAutoren(new AutorDAO().findeAutorZuBuch(b.getBuchId()));
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


	public Buch findByBarcode(String barcode) {
		ResultSet rs = null;
		Buch b = new Buch();
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
				+ "b.id, "
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
				b.setBarcode(rs.getString(count++));
				b.setPreis(rs.getBigDecimal(count++));
				//					b.setErscheinungsJahr(rs.getString(count++));
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
				b.setBuchId(rs.getInt(count++));
				b.setAuflage(rs.getString(count++));
				b.setAutoren(new AutorDAO().findeAutorZuBuch(b.getBuchId()));
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

	public Boolean BarcodeNichtZugeordnet(int barCode) {
		Boolean barcodeNichtZugeordnet = true;
		ResultSet rs = null;
		String sql = "SELECT "
				+ "1 "
				+ "FROM medium "
				+ "WHERE barcode = ?";

		try {

			conn = dbConnection.getDBConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,barCode);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				barcodeNichtZugeordnet = false;					
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

		return barcodeNichtZugeordnet;
	}	
}
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import domain.Anrede;
import domain.Autor;
import domain.Mitarbeiter;
import hilfsklassen.SQLHelfer;
import interfaces.DAOInterface;
import services.HashRechner;

/**
 * @version 3.1 2018-11-10
 * @author Mike
 *
 */
public class MitarbeiterDAO implements DAOInterface<Mitarbeiter> {

	private DBConnection dbConnection = null;
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private List<Mitarbeiter> mitarbeiterListe = null;

	public MitarbeiterDAO() {
		mitarbeiterListe = new ArrayList<>();
		dbConnection = DBConnection.getInstance();
	}

	@Override
	public Mitarbeiter save(Mitarbeiter domainObject) {
		boolean b;
		ResultSet rs = null;
		Mitarbeiter m = new Mitarbeiter();
		int ma_id = 1;
		int argCounter = 0;
		String sql = "INSERT INTO " + "mitarbeiter " + "(benutzername, " + "passwort, " + "admin, " + "aktiv " + ") "
				+ "VALUES " + "(?,?,?,?)";
		try {
			conn = dbConnection.getDBConnection();
			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			argCounter++;
			pstmt.setString(argCounter, domainObject.getBenutzername());
			argCounter++;
			pstmt.setString(argCounter, HashRechner.hashBerechnen(domainObject.getPasswort()));
			argCounter++;
			pstmt.setBoolean(argCounter, domainObject.isAdmin());
			argCounter++;
			pstmt.setBoolean(argCounter, domainObject.isAktiv());
			pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys();
			if(rs != null && rs.next()){
				ma_id = rs.getInt(1);
			}
			m.setMAId(ma_id);
			m.setId(domainObject.getId());
			
			b = new BenutzerDAO().updateMAID(m.getId(), m.getMAId());
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
		return m;
	}

	@Override
	public Mitarbeiter update(Mitarbeiter domainObject) {
		ResultSet rs = null;
		Mitarbeiter m = new Mitarbeiter();
		String sql = "UPDATE mitarbeiter SET "
				+ "benutzername = ? "
				+ ",passwort = ?"
				+ ",aktiv = ?"
				+ ",admin = ?"
				+ " WHERE id = ?";
			try {
				conn = dbConnection.getDBConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1,domainObject.getBenutzername());
				pstmt.setString(2,HashRechner.hashBerechnen(domainObject.getPasswort()));
				pstmt.setBoolean(3, domainObject.isAktiv());
				pstmt.setBoolean(4, domainObject.isAdmin());
				pstmt.setInt(5,  domainObject.getMAId());
				int i = pstmt.executeUpdate();
				if (i>0) {
					m = domainObject;
				}
				else {
					m = null;
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
			
		return m;
	}

	/**
	 * @return Bei Erfolg: true, bei Misserfolg: false
	 */
	@Override
	public boolean delete(Mitarbeiter domainObject) {
				ResultSet rs = null;
				boolean geloescht = false;
				String sql = "DELETE FROM "
						+ "mitarbeiter "
						+ "WHERE id = ?";
					try {			
						conn = dbConnection.getDBConnection();
						pstmt = conn.prepareStatement(sql);
						pstmt.setInt(1, domainObject.getMAId());
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
	public List<Mitarbeiter> getSelektion(Mitarbeiter domainObject) {
		ResultSet rs = null;
		// Mitarbeiter m = null;
		String sql = "SELECT " + "ma.benutzername, " + "ma.passwort, " + "ma.admin, " + "ma.aktiv, " + "p.id, "
				+ "p.vorname, " + "p.nachname, "+ "ma.id " + "FROM mitarbeiter ma "
				+ "INNER JOIN person p on p.mitarbeiter_id = ma.id ";

		// Admin-Flag wird immer abgefragt, daher mit WHERE
		sql = sql + ("WHERE aktiv = ? ");

		if (domainObject.getBenutzername() != null) {

			sql = sql + "AND benutzername";
			sql = sql + (SQLHelfer.likePruefung(domainObject.getBenutzername()) ? " LIKE" : " =");
			sql = sql + " ?";
		}
		// Passwort darf nie mit Wildcard abgefragt werden
		if (domainObject.getPasswort() != null) {
			sql = sql + ("AND passwort = ? ");
		}
		// Vorname
		if (domainObject.getVorname() != null) {
			sql = sql + "AND p.vorname";
			sql = sql + (SQLHelfer.likePruefung(domainObject.getVorname()) ? " LIKE" : " =");
			sql = sql + " ?";
		}
		// Nachname
		if (domainObject.getName() != null) {
			sql = sql + "AND p.nachname";
			sql = sql + (SQLHelfer.likePruefung(domainObject.getName()) ? " LIKE" : " =");
			sql = sql + " ?";
		}

		try {
			int pCounter = 1;
			conn = dbConnection.getDBConnection();
			pstmt = conn.prepareStatement(sql);

			// pstmt.setBoolean(pCounter++, domainObject.isAdmin());
			pstmt.setBoolean(pCounter++, domainObject.isAktiv());

			if (domainObject.getBenutzername() != null) {
				pstmt.setString(pCounter++, SQLHelfer.SternFragezeichenErsatz(domainObject.getBenutzername()));
			}
			if (domainObject.getPasswort() != null) {
				pstmt.setString(pCounter++, HashRechner.hashBerechnen(domainObject.getPasswort()));
			}
			if (domainObject.getVorname() != null) {
				pstmt.setString(pCounter++, SQLHelfer.SternFragezeichenErsatz(domainObject.getVorname()));
			}
			if (domainObject.getName() != null) {
				pstmt.setString(pCounter++, SQLHelfer.SternFragezeichenErsatz(domainObject.getName()));
			}

			rs = pstmt.executeQuery();
			pCounter = 1;
			while (rs.next()) {
				Mitarbeiter m = new Mitarbeiter();

				m.setBenutzername(rs.getString(pCounter++));
				m.setPasswort(rs.getString(pCounter++));
				m.setAdmin(rs.getBoolean(pCounter++));
				m.setAktiv(rs.getBoolean(pCounter++));
				m.setId(rs.getInt(pCounter++));
				m.setVorname(rs.getString(pCounter++));
				m.setName(rs.getString(pCounter++));
				m.setMAId(rs.getInt(pCounter++));
				mitarbeiterListe.add(m);
				pCounter = 1;
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

		return mitarbeiterListe;
	}

	// Hier wird die PersonenId �bergeben. Achtung, Adresse wird im
	// Mitarbeiterobjekt nicht geliefert.
	@Override
	public Mitarbeiter findById(int id) {
		ResultSet rs = null;
		Mitarbeiter m = null;
		String sql = "SELECT " + "ma.id, " + "ma.benutzername, " + "ma.passwort, " + "ma.admin, " + "ma.aktiv, "
				+ "p.id, " + "p.vorname, " + "p.nachname, " + "p.geburtstag, " + "p.telefon, " + "p.bemerkung, "
				+ "p.email, " + "p.erfassungsdatum, " + "p.anrede_id, " + "p.mitarbeiter_id " + "FROM mitarbeiter ma "
				+ "INNER JOIN person p on p.mitarbeiter_id = ma.id ";

		// Admin-Flag wird immer abgefragt, daher mit WHERE
		sql = sql + ("WHERE p.id = ? ");

		try {

			int pCounter = 1;
			conn = dbConnection.getDBConnection();
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			pCounter = 1;
			while (rs.next()) {
				m = new Mitarbeiter();
				m.setMAId(rs.getInt(pCounter++));
				m.setBenutzername(rs.getString(pCounter++));
				m.setPasswort(rs.getString(pCounter++));
				m.setAdmin(rs.getBoolean(pCounter++));
				m.setAktiv(rs.getBoolean(pCounter++));
				m.setId(rs.getInt(pCounter++));
				m.setVorname(rs.getString(pCounter++));
				m.setName(rs.getString(pCounter++));
				m.setGeburtsdatum(rs.getDate(pCounter++));
				m.setTelefon(rs.getString(pCounter++));
				m.setBemerkung(rs.getString(pCounter++));
				m.setEmail(rs.getString(pCounter++));
				m.setErfassungDatum(rs.getDate(pCounter++));
				AnredeDAO anredeDAO = new AnredeDAO();
				Anrede anrede = anredeDAO.findById(rs.getInt(pCounter++));
				m.setAnrede(anrede);
				m.setErfassungMitarbeiterId(rs.getInt(pCounter++));
				String mNameVorname = null;
				MitarbeiterDAO mitarbeiterDAO = new MitarbeiterDAO();
				mNameVorname = mitarbeiterDAO.findNameVornameById(m.getId());
				m.setErfassungMitarbeiterName(mNameVorname);
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
		return m;
	}

	public String findNameVornameById(int id) {
		ResultSet rs = null;
		String nameVorname = null;
		String sql = "SELECT " + "concat(nachname, ' ', vorname) AS nachundvorname " + "FROM person " + "WHERE id = ?";
		try {

			conn = dbConnection.getDBConnection();
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, id);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				nameVorname = (rs.getString(1));
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
		return nameVorname;
	}

	public int findIdByName(String name, String vorname) {
		ResultSet rs = null;
		int id = 0;
		String sql = "SELECT " + "id " + "FROM person " + "WHERE nachname = ? AND vorname = ?";
		try {
			conn = dbConnection.getDBConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setString(2, vorname);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				id = (rs.getInt(1));
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
		return id;
	}
	
	public int findMAid(String name, String vorname) {
		ResultSet rs = null;
		int id = 0;
		String sql = "SELECT " + "id " + "FROM person " + "WHERE nachname = ? AND vorname = ?";
		try {
			conn = dbConnection.getDBConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setString(2, vorname);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				id = (rs.getInt(1));
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
		return id;
	}

	@Override
	public List<Mitarbeiter> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 
	 * @param benutzername
	 * @param passwort
	 * @return int id der Personenid, -1 falls kein Treffer
	 */
	public int loginPruefung(String benutzername, String passwort) {
		int id = -1;
		ResultSet rs = null;
		String sql = "SELECT " + "p.id " + "FROM mitarbeiter ma " + "INNER JOIN person p on p.mitarbeiter_id = ma.id "
				+ "WHERE aktiv = TRUE " + "AND benutzername = ? " + "AND passwort = ?";
		try {

			int pCounter = 1;
			conn = dbConnection.getDBConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(pCounter++, benutzername);
			pstmt.setString(pCounter++, HashRechner.hashBerechnen(passwort));
			rs = pstmt.executeQuery();
			while (rs.next()) {
				id = (rs.getInt(1));
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

		return id;
	}

}

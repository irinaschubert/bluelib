package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Stellt eine Datenbankverbindung zur Verfuegung
 * 
 * Die Fehler müssen mit try-catch abgefangen werden, mit Throws-Exception
 * ziehen sich sonst SQL-Fehlerbehandlungen durch alle Schichten hindurch.
 * 
 * Die DB-Verbindung wird bei jedem SQL-Statement auf - und wieder abgebaut. Der
 * Abbau muss in einem finally-Block liegen.
 * 
 * @version 0.1 16.10.2018
 * @author Schmutz
 *
 */
public class DBConnection {

	public DBConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static Connection getDBConnection() throws SQLException {

		return DriverManager.getConnection(DBConnectionInfo.getConnString());
	}

	public static void closeConnection(Connection conn) throws SQLException {

		conn.close();
	}

}

package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Stellt eine Datenbankverbindung als Singleton zur Verfuegung
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
public final class DBConnection {

	private static final DBConnection INSTANCE = new DBConnection();
	private String dbConnectionString;
	DBConnectionInfo dbConnectionInfo;
	private DBConnection() {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		dbConnectionInfo = DBConnectionInfo.getInstance();
		
	}
	
	public static DBConnection getInstance() {
		return INSTANCE;
	}

	public  Connection getDBConnection() throws SQLException {

		return DriverManager.getConnection(dbConnectionInfo.getConnString());
	}

	public void closeConnection(Connection conn) throws SQLException {

		conn.close();
	}

}

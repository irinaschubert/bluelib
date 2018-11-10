package dao;

import java.util.Properties;
import java.io.*;

/**
 * Liest die Verbindungsparameter aus einem Config-Files aus und bildet den
 * Connectionstring
 * 
 * @version 0.1 16.10.2018
 * @author Schmutz
 *
 */
public class DBConnectionInfo {

	private String dbUser;
	private String dbpw;
	private String dataBase;
	private String dbServer;
	private static final DBConnectionInfo INSTANCE = new DBConnectionInfo();

	private DBConnectionInfo() {
		connStringAufbereiten();
	}

	public static DBConnectionInfo getInstance() {
		return INSTANCE;
	}

	/**
	 * Setzt den Datenbankverbindungsstring zusammen
	 * 
	 * @return Datenbankverbindungsstring
	 */
	public String getConnString() {
		return "jdbc:mysql://" + dbServer + "/" + dataBase + "?user=" + dbUser + "&password=" + dbpw
				+ "&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true";
	}

	/**
	 * Setzt den Datenbankverbindungsstring zusammen
	 * 
	 * @return Datenbankverbindungsstring
	 */
	private void connStringAufbereiten() {
		Properties prop = new Properties();
		try {
			// Name des Konfigurationsfiles
			String fileName = "app.config";
			ClassLoader classLoader = Properties.class.getClassLoader();
			// Ohne Pfadname wird das File nicht gefunden
			String path = Properties.class.getResource("/").getPath();

			InputStream in = new BufferedInputStream(new FileInputStream(path + fileName));

			// Laden des Files
			prop.load(in);

			// Lesen der werte
			dbUser = prop.getProperty("dbUser");
			dbpw = prop.getProperty("dbpw");
			dataBase = prop.getProperty("dataBase");
			dbServer = prop.getProperty("dbServer");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

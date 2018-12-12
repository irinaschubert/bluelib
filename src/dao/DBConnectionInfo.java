package dao;

import java.util.Objects;
import java.util.Properties;
import java.io.*;

/**
 * Liest die Verbindungsparameter aus einem Config-Files aus und bildet den
 * Connectionstring.
 * 
 * @version 0.1 16.10.2018
 * @author Ueli
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
			String path;
			// Der Pfadname wird dynamisch angepasst, abhangig davon, ob die Applikation aus
			// einem Jar-File heraus gestartet wird.
			if (isDevelopmentEnvironment()) {
				// Ohne Pfadname wird das File nicht gefunden
				path = Properties.class.getResource("/").getPath();
			} else {
				File directory = new File("./config/");
				path = directory.toString() + "/";

			}

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

	/**
	 * 
	 * @return false, falls die Applikation nicht aus einem Jar-File gestartet wird.
	 */
	public boolean isDevelopmentEnvironment() {
		Boolean isDev = false;

		String protocol = this.getClass().getResource("").getProtocol();
		if (Objects.equals(protocol, "jar")) {
			isDev = false;
		} else if (Objects.equals(protocol, "file")) {
			isDev = true;
		}

		return isDev;
	}
}

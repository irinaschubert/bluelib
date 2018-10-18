package dao;

import java.util.ResourceBundle;

/**
 * Stellt die DB-Verbindungsinformationen zur Verfuegung
 * Ziel ist es, die Verbindugnsdaten in einem File zu platzieren und dieses auszulesen
 * 
 * @version 0.1 16.10.2018
 * @author Schmutz
 *
 */
public class DBConnectionInfo {
	
	private static String dbUser = "root" ;
	private static String dbpw = "bluelib$2018";
	private static String dataBase = "bluelib";
	private static String dbServer = "localhost";
	private ResourceBundle rb;
	
	/*
	 * Setzt den Datenbankverbindungsstring zusammen
	 * @return Datenbankverbindungsstring
	 */
	public static String getConnString() {
		return "jdbc:mysql://" + dbServer + "/" + dataBase + "?user=" + dbUser +  "&password=" + dbpw + "&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true";
	}
}

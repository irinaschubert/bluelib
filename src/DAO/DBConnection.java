package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Stellt eine Datenbankverbindung zur Verfuegung
 * @author Schmutz
 *
 */
public class DBConnection {
	
	private Connection dbConnection  = null;
	
	public DBConnection() {
    try {
    	Class.forName("com.mysql.cj.jdbc.Driver");
    }
    catch (Exception ex) {
    	ex.printStackTrace();	    	
    }
	}
	
	
	
	
	 public void openConnection() throws SQLException{
		   //conn =  DriverManager.getConnection("jdbc:mysql://localhost/" + database + "?user=" + dbuser +  "&password=" + dbpwd + "&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
	   //dbServer = "192.168.1.2";    
		 dbConnection =  DriverManager.getConnection(DBConnectionInfo.getConnString());
 }
 
 public void closeConnection() throws SQLException{	   
	 	dbConnection.close();	   
 }



}

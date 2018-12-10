package hilfsklassen;

/**
 * 
 * Hilft beim Prüfen und Formatieren von SQL-Statements
 * 
 * @version 1.0 30.10.2018
 * @author Ueli
 *
 */
public class SQLHelfer {
	
	/**
	 * Prueft, ob der String * oder ? enthaelt
	 */
	public static boolean likePruefung(String string) {
		boolean returnValue = false;
		if (	string.contains("*")
				|| string.contains("?")) {
			returnValue = true;
		}
		
		return returnValue;
	}
	
	/**
	 * Ersetzt * mit % und ? mit _ --> mysql-Such-Characters
	 */
	public static String SternFragezeichenErsatz(String string) {
		String returnValue = string;
		
		returnValue = returnValue.replace("*", "%");
		returnValue = returnValue.replace("?", "_");
		return returnValue;
	}

}

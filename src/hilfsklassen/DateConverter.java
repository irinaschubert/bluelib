package hilfsklassen;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Die Klasse DateConverter wird verwendet, um Java- zu MySQL-Daten zu
 * konvertieren und umgekehrt.
 * 
 * @version 0.1 16.10.2018
 * @author irina
 */
public class DateConverter {

	public java.sql.Date convertJavaDateToSQLDate(java.util.Date datum) {
		java.sql.Date sqlDate = new java.sql.Date(datum.getTime());
		return sqlDate;
	}

	/**
	 * Formatiert Daten aus Domänenobjekten in SQL-Format
	 * 
	 * @param datum Date
	 * @return String
	 */
	public static java.sql.Date convertJavaDateToSQLDateN(java.util.Date datum) {
		java.sql.Date sqlDate = new java.sql.Date(datum.getTime());
		return sqlDate;
	}

	/**
	 * Formatiert Daten aus Domänenobjekten in String
	 * 
	 * @param datum Date
	 * @return String
	 */
	public static String convertJavaDateToString(Date datum) {
		String returnValue = "";
		if (datum != null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
			returnValue = dateFormat.format(datum);
		}
		return returnValue;
	}

	/**
	 * Formatiert Daten aus TextFeldern zu Java-Date
	 * 
	 * @param datum String
	 * @return String
	 */
	public static Date convertStringToJavaDate(String datum) {
		// HH:mm:ss wird benötigt, damit das Datum korrekt in mysql gespeichert wird.
		SimpleDateFormat dateOut = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat dateIn = new SimpleDateFormat("dd.MM.yyyy");
		// Ohne UTC-Zeit speichert mysql das Datum mit Zeitverschiebung = -1 Tag
		dateIn.setTimeZone(TimeZone.getTimeZone("UTC"));
		Date returnDatum = new Date();
		if (!datum.equals("")) {
			try {
				String datumConv = dateOut.format(dateIn.parse(datum));
				returnDatum = dateOut.parse(datumConv);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		return returnDatum;
	}

	/**
	 * Prüft, ob ein eingegebenes Datum gültig ist.
	 * 
	 * @param date String
	 * @return boolean
	 */
	public static boolean datumIstGueltig(final String date) {
		String[] formatStrings = { "dd.MM.yyyy" };
		boolean istGueltigesFormat = false;
		Date dateObj;
		for (String formatString : formatStrings) {
			try {
				SimpleDateFormat sdf = (SimpleDateFormat) DateFormat.getDateInstance();
				sdf.applyPattern(formatString);
				sdf.setLenient(false);
				dateObj = sdf.parse(date);
				if (date.equals(sdf.format(dateObj))) {
					istGueltigesFormat = true;
					break;
				}
			} catch (ParseException e) {
				istGueltigesFormat = false;
			}
		}
		return istGueltigesFormat;
	}
}

package hilfsklassen;

public class DateConverter {
	
	public java.sql.Date convertJavaDateToSQLDate(java.util.Date datum) {
		java.sql.Date sqlDate = new java.sql.Date(datum.getTime());
		return sqlDate;
	}

}

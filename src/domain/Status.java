package domain;

/**
 * @version 0.1 16.10.2018
 * @author irina
 *
 */
public class Status {

	private int id;
	private String bezeichnung;
	final static public String AKTIV = "Aktiv";
	final static public String GESPERRT = "Gesperrt";
	final static public String GELOESCHT = "Gelöscht";
	
	public Status() {
		
	}
	
	public static String[] getStatus() {
		String[] anreden = {"aktiv", "gesperrt", "gelöscht"};
		return anreden;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBezeichnung() {
		return bezeichnung;
	}

	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}
	
}

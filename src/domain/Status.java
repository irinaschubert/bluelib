package domain;

/**
 * @version 0.1 16.10.2018
 * @author irina
 *
 */
public class Status {

	private int id;
	private String bezeichnung;
	final static public int AKTIV = 0;
	final static public int GESPERRT = 1;
	final static public int GELOESCHT = 2;
	
	public Status() {
		
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

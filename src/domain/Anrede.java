package domain;

/**
 * Die Klasse der Anrede-Objekte. Anrede-Objekte werden für Benutzer verwendet.
 * 
 * @version 0.1 16.10.2018
 * @author Irina
 * 
 */
public class Anrede {

	private int id;
	private String bezeichnung;

	public Anrede(int id, String bezeichnung) {
		this.id = id;
		this.bezeichnung = bezeichnung;
	}

	public Anrede() {

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

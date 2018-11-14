package domain;

/**
 * @version 0.1 16.10.2018
 * @author irina
 *
 */
public class Status {

	private int id;
	private String bezeichnung;
	
	public Status(int id, String bezeichnung) {
		this.id = id;
		this.bezeichnung = bezeichnung;
	}
	
	public Status() {
		this.id = 0;
		this.bezeichnung = "";
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

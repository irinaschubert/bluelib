package domain;

/**
 * @version 0.1 16.10.2018
 * @author irina
 *
 */
public class Anrede {
	
	private int id;
	private String bezeichnung;	

	public Anrede(int id, String bezeichnung) {
		this.id = id;
		this.bezeichnung = bezeichnung;
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

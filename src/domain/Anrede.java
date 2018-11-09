package domain;

/**
 * @version 0.1 16.10.2018
 * @author irina
 *
 */
public class Anrede {
	
	private int id;
	private String bezeichnung;
	final static public String FRAU = "Frau";
	final static public String HERR = "Herr";
	

	public Anrede() {
		
	}
	
	public static String[] getAnreden() {
		String[] anreden = {"Frau", "Herr"};
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

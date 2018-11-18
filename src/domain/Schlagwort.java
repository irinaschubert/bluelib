package domain;

/**
 * @version 0.1 16.10.2018
 * @author irina
 *
 */
public class Schlagwort {

	private int id;
	private String schlagwort;
	private boolean geloescht;

	public Schlagwort() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSchlagwort() {
		return schlagwort;
	}

	public void setSchlagwort(String schlagwort) {
		this.schlagwort = schlagwort;
	}

	public boolean getGeloescht() {
		return geloescht;
	}

	public void setGeloescht(boolean geloescht) {
		this.geloescht = geloescht;
	}

}

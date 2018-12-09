package domain;

/**
 * @version 0.1 16.10.2018
 * @author irina
 *
 */
public class Mitarbeiter extends Person {

	private int idMA;
	private String benutzername;
	private String passwort;
	private boolean admin;
	private boolean aktiv;

	public Mitarbeiter() {

	}

	public int getMAId() {
		return idMA;
	}

	public void setMAId(int idMA) {
		this.idMA = idMA;
	}

	public String getBenutzername() {
		return benutzername;
	}

	public void setBenutzername(String benutzername) {
		this.benutzername = benutzername;
	}

	public String getPasswort() {
		return passwort;
	}

	public void setPasswort(String passwort) {
		this.passwort = passwort;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public boolean isAktiv() {
		return aktiv;
	}

	public void setAktiv(boolean aktiv) {
		this.aktiv = aktiv;
	}
}

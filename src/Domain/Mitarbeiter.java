package Domain;

/**
 * @version 0.1 16.10.2018
 * @author BlueLib
 *
 */
public class Mitarbeiter {
	
	String benutzername;
	String passwort;
	boolean admin;
	boolean aktiv;
	
	public Mitarbeiter() {
		
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

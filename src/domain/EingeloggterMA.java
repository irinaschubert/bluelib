package domain;

import dao.DBConnectionInfo;

public class EingeloggterMA {
	private static final EingeloggterMA INSTANCE = new EingeloggterMA();
	private Mitarbeiter mitarbeiter;

	

	public static EingeloggterMA getInstance() {
		return INSTANCE;
	}


	public Mitarbeiter getMitarbeiter() {
		return mitarbeiter;
	}


	public void setMitarbeiter(Mitarbeiter mitarbeiter) {
		this.mitarbeiter = mitarbeiter;
	}
	
	
	
	
}

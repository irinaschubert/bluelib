package domain;

public class EingeloggterMA extends Mitarbeiter {
	private static EingeloggterMA INSTANCE = null;
	private Mitarbeiter mitarbeiter;

	public static EingeloggterMA getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new EingeloggterMA();
	       }
	       return INSTANCE;
	}

	public Mitarbeiter getMitarbeiter() {
		return mitarbeiter;
	}

	public void setMitarbeiter(Mitarbeiter mitarbeiter) {
		this.mitarbeiter = mitarbeiter;
	}
}

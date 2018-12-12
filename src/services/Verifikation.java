package services;

/**
 * Klasse zur Kommunkation von Service zu Controller
 * 
 * @version 0.1 16.10.2018
 * @author Irina
 *
 */
public class Verifikation {
    private String nachricht;
    private boolean aktionErfolgreich;
    
    public Verifikation() {
    	
    }

	public String getNachricht() {
		return nachricht;
	}

	public void setNachricht(String nachricht) {
		this.nachricht = nachricht;
	}

	public boolean isAktionErfolgreich() {
		return aktionErfolgreich;
	}

	public void setAktionErfolgreich(boolean aktionErfolgreich) {
		this.aktionErfolgreich = aktionErfolgreich;
	}
}

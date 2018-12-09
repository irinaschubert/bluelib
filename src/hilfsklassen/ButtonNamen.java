package hilfsklassen;

public enum ButtonNamen {
	SICHERN("Sichern"),
	UEBERNEHMEN("Übernehmen"),
	ABBRECHEN("Abbrechen"),
	NEU("Neu"),
	SCHLIESSEN("Schliessen"),
	ANMELDEN("Anmelden"),
	ZURUECKGABE("Zur Rückgabe"),
	AUSLEIHE("Zur Ausleihe");
	
	private final String name;
	
	private ButtonNamen(final String name){
		this.name = name;
	}
	
    public String getName() {
        return name;
     }
}

package hilfsklassen;

/**
 * Stellt Beschriftungen der Buttons zur Verfügung: das Naming soll identisch sein
 * 
 * @version 1.0 2018-10-1
 * @author Ueli
 *
 */

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

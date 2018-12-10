package hilfsklassen;

/**
 * Stellt Beschriftungen der Buttons zur Verf�gung: das Naming soll identisch sein
 * 
 * @version 1.0 2018-10-1
 * @author Ueli
 *
 */

public enum ButtonNamen {
	SICHERN("Sichern"),
	UEBERNEHMEN("�bernehmen"),
	ABBRECHEN("Abbrechen"),
	NEU("Neu"),
	SCHLIESSEN("Schliessen"),
	ANMELDEN("Anmelden"),
	ZURUECKGABE("Zur R�ckgabe"),
	AUSLEIHE("Zur Ausleihe");
	
	private final String name;
	
	private ButtonNamen(final String name){
		this.name = name;
	}
	
    public String getName() {
        return name;
     }
}

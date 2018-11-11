package hilfsklassen;

public enum ButtonNamen {
	SICHERN("Sichern"),
	UEBERNEHMEN("�bernehmen"),
	ABBRECHEN("Abbrechen"),
	NEU("Neu"),
	SCHLIESSEN("Schliessen"),
	ANMELDEN("Anmelden");
	
	private final String name;
	
	private ButtonNamen(final String name){
		this.name = name;
	}
	
    public String getName() {
        return name;
     }
}

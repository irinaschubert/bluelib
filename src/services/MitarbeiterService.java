package services;

import domain.Mitarbeiter;

/**
 * @version 0.1 16.10.2018
 * @author irina
 *
 */
public class MitarbeiterService {
    public MitarbeiterService() {}
    
    public Verifikation darfEinloggen(String name, String pw) {
    	Verifikation v = new Verifikation();
		return v;
    }
    
    private boolean immerEinAdmin(Mitarbeiter mitarbeiter) {
    	boolean yes = true;
    	return yes;
    }
    
    public Verifikation mitarbeiterErfassen(Mitarbeiter mitarbeiter) {
    	Verifikation v = new Verifikation();
		return v;
    }
    
    public Verifikation mitarbeiterBearbeiten(Mitarbeiter mitarbeiter) {
    	Verifikation v = new Verifikation();
		return v;
    }
}

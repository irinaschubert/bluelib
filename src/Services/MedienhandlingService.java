package Services;

import java.util.ArrayList;

import Domain.Benutzer;
import Domain.Medium;
import Domain.Buch;

/**
 * @version 0.1 16.10.2018
 * @author irina
 *
 */
public class MedienhandlingService {
	
	public MedienhandlingService() {}
	
	public boolean istVerfuegbar(Medium medium) {
		boolean yes = true;
		return yes;
	}
	
	public Verifikation ausleihen(Medium medium, Benutzer benutzer) {
		Verifikation v = new Verifikation();
		return v;
	}
	
	public Verifikation isbnPruefen(String isbn) {
		Verifikation v = new Verifikation();
		return v;
	}
	
	public Verifikation zuordnungBarcodeUeberpruefen(long barcode) {
		Verifikation v = new Verifikation();
		return v;
	}
	
	public Benutzer ausgeliehenDurch(Medium medium) {
		Benutzer b = new Benutzer();
		return b;
	}
	
	public Verifikation zurueckgeben(Medium medium) {
		Verifikation v = new Verifikation();
		return v;
	}
	
	public Verifikation mediumNeuErfassen(Medium medium) {
		Verifikation v = new Verifikation();
		return v;
	}
	
	public Verifikation mediumBearbeiten(Medium medium) {
		Verifikation v = new Verifikation();
		return v;
	}
	
	public ArrayList<Medium> mediumSuchen(Medium medium) {
		ArrayList<Medium> m = new ArrayList<>();
		return m;
	}
	
	public Medium mediumScannen(long barcode) {
		Buch b = new Buch();
		return b;
	}

}

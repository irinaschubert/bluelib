package services;

import java.util.ArrayList;
import java.util.List;

import dao.BuchDAO;
import dao.MedienStatusDAO;
import dao.VerlagDAO;
import domain.Benutzer;
import domain.Buch;
import domain.Medium;
import domain.Status;

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
	
	public List<Buch> BuchSuchen(Buch buch) {
		return new BuchDAO().getSelektion(buch);
	}
	
	public Medium mediumScannen(long barcode) {
		Buch b = new Buch();
		return b;
	}
	
	public List<Status> alleMedienStati(){
		return new MedienStatusDAO().findAll();
	}

}

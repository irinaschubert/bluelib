package services;

import java.util.List;
import dao.BuchDAO;
import dao.MedienStatusDAO;
import domain.Buch;
import domain.Status;

/**
 * Bietet die Services zur Verwaltung der Medien an.
 * 
 * @version 0.1 16.10.2018
 * @author Ueli
 *
 */
public class MedienhandlingService {
	
	public MedienhandlingService() {}
	
	/**
	 * Prueft, ob es sich beim Parameter um einen validen Barcode handelt
	 * 
	 * @param barcode
	 * @return Barcode valide: true, Barcode falsch: false
	 */
	public Verifikation istBarcode(String barcode) {
		Verifikation v = new Verifikation();
		v.setAktionErfolgreich(true);
		try 
        { 
            // checking valid integer using parseInt() method 
            Integer.parseInt(barcode); 
        }  
        catch (NumberFormatException e)  
        { 
            v.setAktionErfolgreich(false);
            v.setNachricht("Der Barcode darf nur aus Zahlen bestehen");
        } 
		
		if (!(barcode.length() == 7)) {
			v.setAktionErfolgreich(false);
			v.setNachricht("Der Barcode muss 7 Ziffern lang sein.");
		}
		
		return v;
	}
	
	public Verifikation BarcodeZugeordnet(int barCode) {
				
			Verifikation v = new Verifikation();
			v.setAktionErfolgreich(false);
			BuchDAO buchDAO = new BuchDAO();
			if (buchDAO.BarcodeZugeordnet(barCode)) {
				v.setAktionErfolgreich(true);
				v.setNachricht("Dieser Barcode ist bereits einem Medium zugeordnet.");
			}
			else {
				v.setAktionErfolgreich(false);
				v.setNachricht("Dieser Barcode ist keinem einem Medium zugeordnet.");
				
			}
			return v;
		}	
	
	public Verifikation speichernBuch(Buch buch) {
		Verifikation v = new Verifikation();
		Buch b = null;
		BuchDAO buchDAO = new BuchDAO();
		b = buchDAO.save(buch);
		if (b != null) {
			v.setAktionErfolgreich(true);
			v.setNachricht("Das Buch '" + b.getTitel() +  "' wurde erfasst");
		}
		else {
			v.setAktionErfolgreich(false);
			v.setNachricht("Das Buch '" + buch.getTitel() + "' konnte nicht gespeichert werden");
		}
		
		return v;
	}
	
	public Verifikation buchBearbeiten(Buch buch) {
		Verifikation v = new Verifikation();
		Buch b = null;
		BuchDAO buchDAO = new BuchDAO();
		b = buchDAO.update(buch);
		if (b != null) {
			v.setAktionErfolgreich(true);
			v.setNachricht("Die Anderungen des Buchs '" + b.getTitel() +  "' wurde gespeichert");
		}
		else {
			v.setAktionErfolgreich(false);
			v.setNachricht("Die Anderungen des Buchs '" + buch.getTitel() + "' konnten nicht gespeichert werden");
		}
		
		return v;
	}
	
	public List<Buch> suchenBuch(Buch buch) {
		return new BuchDAO().getSelektion(buch);
	}
	
	public List<Status> alleMedienStati(){
		return new MedienStatusDAO().findAll();
	}

}

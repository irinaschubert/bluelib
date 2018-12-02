package hilfsklassen;

import dao.BuchDAO;
import services.MedienhandlingService;
import services.Verifikation;

public class BarcodePruefung {
	
	public static Verifikation istBarcode(String barcode) {
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
	
	public static Verifikation BarcodeNichtZugeordnet(String barCode) {
		int barCodeInt = Integer.parseInt(barCode); 
		Verifikation v = new Verifikation();
		v.setAktionErfolgreich(false);
		BuchDAO buchDAO = new BuchDAO();
		if (buchDAO.BarcodeNichtZugeordnet(barCodeInt)) {
			v.setAktionErfolgreich(true);
		}
		else {
			v.setNachricht("Dieser Barcode ist bereits einem Medium zugeordnet.");
		}
		return v;
	}

}

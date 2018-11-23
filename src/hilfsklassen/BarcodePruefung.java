package hilfsklassen;

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

}

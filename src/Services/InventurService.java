package Services;

import java.util.Date;
import java.util.ArrayList;
import Domain.Person;
import Domain.Medium;
import Domain.Inventur;

/**
 * @version 0.1 16.10.2018
 * @author irina
 *
 */
public class InventurService {
	
	public InventurService() {}
	
	public Verifikation neueInventur(Date datum, String bezeichnung, Person mitarbeiter) {
		Verifikation v = new Verifikation();
		return v;
	}
	
	public ArrayList<Medium> inventurdifferenz(Inventur inventur){
		ArrayList<Medium> list = new ArrayList<>();
		return list;
	}
	
	public Verifikation mediumBuchen(Medium medium) {
		Verifikation v = new Verifikation();
		return v;
	}

}

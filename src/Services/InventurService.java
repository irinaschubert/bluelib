package Services;

import java.util.Date;
import java.util.List;
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
		return Verifikation;
	}
	
	public List<Medium> inventurdifferenz(Inventur inventur){
		return List;
	}
	
	public Verifikation mediumBuchen(Medium medium) {
		return Verifikation;
	}

}

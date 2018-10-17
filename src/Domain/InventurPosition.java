package Domain;

import java.util.Date;

/**
 * @version 0.1 16.10.2018
 * @author irina
 *
 */
public class InventurPosition {
	
	Medium medium;
	Date datum;
	
	public InventurPosition() {
		
	}

	public Medium getMedium() {
		return medium;
	}

	public void setMedium(Medium medium) {
		this.medium = medium;
	}

	public Date getDatum() {
		return datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}
	
	

}

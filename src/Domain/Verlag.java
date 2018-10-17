package Domain;

import java.util.Date;

/**
 * @version 0.1 16.10.2018
 * @author BlueLib
 *
 */
public class Verlag {
	
	int id;
	String name;
	Date gruendungsDatum;
	Date endDatum;
	
	public Verlag() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getGruendungsDatum() {
		return gruendungsDatum;
	}

	public void setGruendungsDatum(Date gruendungsDatum) {
		this.gruendungsDatum = gruendungsDatum;
	}

	public Date getEndDatum() {
		return endDatum;
	}

	public void setEndDatum(Date endDatum) {
		this.endDatum = endDatum;
	}
	
	

}

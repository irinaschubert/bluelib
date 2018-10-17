package Domain;

import java.util.Date;

/**
 * @version 0.1 16.10.2018
 * @author irina
 *
 */
public class Autor {
	
	int id;
	String name;
	String vorname;
	Date geburtsdatum;
	Date todesdatum;
	
	public Autor() {
		
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

	public String getVorname() {
		return vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public Date getGeburtsdatum() {
		return geburtsdatum;
	}

	public void setGeburtsdatum(Date geburtsdatum) {
		this.geburtsdatum = geburtsdatum;
	}

	public Date getTodesdatum() {
		return todesdatum;
	}

	public void setTodesdatum(Date todesdatum) {
		this.todesdatum = todesdatum;
	}

	
}

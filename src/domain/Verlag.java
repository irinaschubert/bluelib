package domain;

import java.util.Date;

/**
 * Die Klasse für Verlag-Objekte. Ein Verlag hat einen Namen, ein Gründungs-
 * und ein Enddatum. Ein Verlag ist ein Normadatensatz.
 * @version 0.1 16.10.2018
 * @author irina
 */
public class Verlag {
	
	private int id;
	private String name;
	private Date gruendungsDatum;
	private Date endDatum;
	private boolean geloescht;
	
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
	
	public boolean getGeloescht() {
		return geloescht;
	}

	public void setGeloescht(boolean geloescht) {
		this.geloescht = geloescht;
	}
	
	

}

package domain;

import dao.StatusDAO;

/**
 * Die Klasse der Status-Objekte. Status-Objekte werden sowohl für Benutzer
 * als auch für Bücher verwendet.
 * @version 0.1 16.10.2018
 * @author irina
 */
public class Status {

	private int id;
	private String bezeichnung;
	
	public Status(int id, String bezeichnung) {
		this.id = id;
		this.bezeichnung = bezeichnung;
	}
	
	public Status(int id) {
		this.id = id;
	}
	
	public Status() {
		this.id = 0;
		this.bezeichnung = "";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBezeichnungFromId(int id) {
		StatusDAO statusDao = new StatusDAO();
		Status s = statusDao.findById(id);
		return s.getBezeichnung();
	}
	
	public String getBezeichnung() {
		return bezeichnung;
	}

	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}
	
}

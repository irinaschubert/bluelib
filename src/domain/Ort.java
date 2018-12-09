package domain;

/**
 * Die Klasse der Ort-Objekte. Ein Ort hat eine id,
 * besteht aus einer Postleitzahl (PLZ) und einem Ortsnamen 
 * @version 0.1 16.10.2018
 * @author irina
 */
public class Ort {
	private int id;
	private int plz;
	private String ort;
	
	public Ort() {
    	this.id = 0;
    	this.plz = 0;
    	this.ort = "";
    }
    
	public Ort(int id) {
    	this.id = id;
    	this.plz = 0;
    	this.ort = "";
    }
    
	public Ort(int id, int plz, String ort) {
    	this.id = id;
    	this.plz = plz;
    	this.ort = ort;
    }
    
    public Ort(int id, int plz) {
    	this.plz = plz;
    	this.id = id;
    }
    
    public Ort(int id, String ort) {
    	this.ort = ort;
    	this.id = id;
    }
    
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getPlz() {
		return plz;
	}
	
	public void setPlz(int plz) {
		this.plz = plz;
	}
	
	public String getOrt() {
		return ort;
	}
	
	public void setOrt(String ort) {
		this.ort = ort;
	}
}

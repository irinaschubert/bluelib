package domain;

/**
 * @version 0.1 16.10.2018
 * @author irina
 *
 */
public class Ort {
	private int id;
	private int plz;
	private String ort;
    
    public Ort(int plz, String ort) {
    	this.plz = plz;
    	this.ort = ort;
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

package Domain;

/**
 * @version 0.1 16.10.2018
 * @author BlueLib
 *
 */

public class Ort {
    int id;
    int plz;
    String ort;
    
    public Ort() {
    	
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

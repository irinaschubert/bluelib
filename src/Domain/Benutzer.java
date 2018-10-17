package Domain;

/**
 * @version 0.1 16.10.2018
 * @author irina
 *
 */
public class Benutzer extends Person {

	private Status status;
	
	public Benutzer() {
		
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
	
}

package services;

import java.util.List;

import dao.OrtDAO;
import domain.Ort;

/**
 * Die Serviceklasse vermittelt zwischen dem BenutzerController und dem
 * BenutzerDAO.
 * 
 * @version 0.1 16.10.2018
 * @author Irina
 * 
 */
public class OrtService {

	public OrtService() {
	}
	
	public Ort suchenOrtById(int id) {
		return new OrtDAO().findById(id);
	}
	
	public List<Ort> suchenAlleOrte(){
		return new OrtDAO().findAll();
	}

}

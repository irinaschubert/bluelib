package services;

import java.util.List;

import dao.AnredeDAO;
import domain.Anrede;

/**
 * Die Serviceklasse vermittelt zwischen dem BenutzerController und dem
 * BenutzerDAO.
 * 
 * @version 0.1 16.10.2018
 * @author Irina
 * 
 */
public class AnredeService {

	public AnredeService() {
	}
	
	public List<Anrede> suchenAlleAnreden() {
		return new AnredeDAO().findAll();
	}

}

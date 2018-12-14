package services;

import java.util.List;

import dao.StatusDAO;
import domain.Status;

/**
 * Die Serviceklasse vermittelt zwischen dem BenutzerController und dem
 * BenutzerDAO.
 * 
 * @version 0.1 16.10.2018
 * @author Irina
 * 
 */
public class StatusService {

	public StatusService() {
	}
	
	public List<Status> suchenAlleStatus() {
		return new StatusDAO().findAll();
	}

}

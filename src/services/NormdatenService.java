package services;

import java.util.List;

import domain.Anrede;
import repositories.AnredeRepository;

/**
 * @version 0.1 18.10.2018
 * @author Schmutz
 *
 */

public class NormdatenService {

	public List<Anrede> alleAnreden(){
		return new AnredeRepository().findAll();
	}
}

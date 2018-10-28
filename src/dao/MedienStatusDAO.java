package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import domain.Medium;
import hilfsklassen.DateConverter;
import interfaces.DAOInterface;
/**
 * @version 0.1 28.10.2018
 * @author Mike
 *
 */
public class MedienStatusDAO implements DAOInterface<Medium> {

	@Override
	public Medium save(Medium domainObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Medium update(Medium domainObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(Medium domainObject) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Medium> getSelektion(Medium domainObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Medium findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Medium> findAll() {
		// TODO Auto-generated method stub
		return null;
	}


}

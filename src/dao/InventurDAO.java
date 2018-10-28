package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import domain.Inventur;
import hilfsklassen.DateConverter;
import interfaces.DAOInterface;
/**
 * @version 0.1 28.10.2018
 * @author Mike
 *
 */
public class InventurDAO implements DAOInterface<Inventur> {

	@Override
	public Inventur save(Inventur domainObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Inventur update(Inventur domainObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(Inventur domainObject) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Inventur> getSelektion(Inventur domainObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Inventur findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Inventur> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domain.Anrede;
import domain.Status;
import interfaces.DAOInterface;

/**
 * 
 * @version 0.1 16.10.2018
 * @author Schmutz
 */

public class AnredeDAO implements DAOInterface<Anrede> {

	private DBConnection dbConnection = null;
	private Connection conn = null; 
	private ResultSet mRS = null;
	private PreparedStatement pstmt = null;
	
	public AnredeDAO(){
		dbConnection = DBConnection.getInstance();
	}
	
	@Override
	public Anrede save(Anrede domainObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Anrede update(Anrede domainObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(Anrede domainObject) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Anrede> getSelektion(Anrede domainObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Anrede findById(int id) {
		Anrede a = new Anrede();
		String sql = "SELECT id, bezeichnung from anrede WHERE id = ?";
		try {
			conn = dbConnection.getDBConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,id);
			mRS = pstmt.executeQuery();
			while(mRS.next()) {
				a.setId(mRS.getInt(1));
				a.setBezeichnung(mRS.getString(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return a;
	}
	

	@Override
	public ArrayList<Anrede> findAll() {
		ArrayList<Anrede> allAnrede = new ArrayList<>();
		String sql = "SELECT id, bezeichnung from anrede";
		try {
			conn = dbConnection.getDBConnection();
			pstmt = conn.prepareStatement(sql);
			mRS = pstmt.executeQuery();
			while(mRS.next()) {
				Anrede a = new Anrede();
				a.setId(mRS.getInt(1));
				a.setBezeichnung(mRS.getString(2));
				allAnrede.add(a);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return allAnrede;
	}

	

	

}

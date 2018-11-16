package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import domain.Status;
import interfaces.DAOInterface;

public class StatusDAO implements DAOInterface<Status> {
	
	private DBConnection dbConnection = null;
	private Connection conn = null; 
	private ResultSet mRS = null;
	private PreparedStatement pstmt = null;
	
	public StatusDAO() {
		dbConnection = DBConnection.getInstance();
	}
	
	@Override
	public Status save(Status statusObject) {
		return null;
     }

	@Override
	public Status update(Status statusObject) {			
		return null;
	}

	@Override
	public boolean delete(Status statusObject) {
		return false;
}

	@Override
	public List<Status> getSelektion(Status statusObject) {
		return null;
	}

	@Override
	public Status findById(int id) {
		Status s = new Status();
		String sql = "SELECT id, bezeichnung from statuspers WHERE id = ?";
		try {
			conn = dbConnection.getDBConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,id);
			mRS = pstmt.executeQuery();
			while(mRS.next()) {
				s.setId(mRS.getInt(1));
				s.setBezeichnung(mRS.getString(2));
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
		return s;
	}
	

	@Override
	public ArrayList<Status> findAll() {
		ArrayList<Status> allStatus = new ArrayList<>();
		String sql = "SELECT id, bezeichnung from statuspers";
		try {
			conn = dbConnection.getDBConnection();
			pstmt = conn.prepareStatement(sql);
			mRS = pstmt.executeQuery();
			while(mRS.next()) {
				Status s = new Status();
				s.setId(mRS.getInt(1));
				s.setBezeichnung(mRS.getString(2));
				allStatus.add(s);
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
		return allStatus;
	}

}


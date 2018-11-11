package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import domain.Ort;
import interfaces.DAOInterface;

public class OrtDAO implements DAOInterface<Ort> {
	
	private DBConnection dbConnection = null;
	private Connection conn = null; 
	private ResultSet mRS = null;
	private PreparedStatement pstmt = null;
	
	public OrtDAO() {
		dbConnection = DBConnection.getInstance();
	}
	
	@Override
	public Ort save(Ort domainObject) {
		return null;
     }

	@Override
	public Ort update(Ort domainObject) {			
		return null;
	}

	@Override
	public boolean delete(Ort domainObject) {
		return false;
}

	@Override
	public List<Ort> getSelektion(Ort domainObject) {
		return null;
	}

	@Override
	public Ort findById(int id) {
		Ort o = new Ort();
		String sql = "SELECT id, plz, ort from ort WHERE id = ?";
		try {
			conn = dbConnection.getDBConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,id);
			mRS = pstmt.executeQuery();
			while(mRS.next()) {
				o.setId(mRS.getInt(1));
				o.setPlz(mRS.getInt(2));
				o.setOrt(mRS.getString(3));
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
		return o;
	}
	

	@Override
	public List<Ort> findAll() {
		return null;
	}

}

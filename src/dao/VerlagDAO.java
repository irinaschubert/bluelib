package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import domain.Verlag;
import interfaces.DAOInterface;
import hilfsklassen.DateConverter;

public class VerlagDAO implements DAOInterface<Verlag> {
	
	private DBConnection dbConnection = null;
	private Connection conn = null; 
	private ResultSet mRS = null;
	private PreparedStatement pstmt = null;
	private List<Verlag> verlagListe = null;
	private DateConverter dateConverter;
	
	
	public VerlagDAO() {
		verlagListe = new ArrayList<>();
		dbConnection = DBConnection.getInstance();
		dateConverter = new DateConverter();
	}
	
	@Override
	public Verlag save(Verlag domainObject) {
		ResultSet rs = null;
		Verlag v = new Verlag();		
		String sql = "INSERT INTO "
				+ "verlag "
				+ "(name "
				+ (domainObject.getGruendungsDatum() != null ? ",gruendungsdatum ":"")
				+ (domainObject.getEndDatum() != null?",enddatum ":"")
				+ ") "
				+ "VALUES "
				+ "(? "
				+ (domainObject.getGruendungsDatum() != null?",? ":"")
				+ (domainObject.getEndDatum()!= null?",? ":"")
				+ ")" ;
			try {
				conn = dbConnection.getDBConnection();
				pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				
				pstmt.setString(1,domainObject.getName());
				if (domainObject.getGruendungsDatum() != null) {
					pstmt.setDate(2, dateConverter.convertJavaDateToSQLDate(domainObject.getGruendungsDatum()));
				}
				if (domainObject.getEndDatum() != null) {
					pstmt.setDate(3, dateConverter.convertJavaDateToSQLDate(domainObject.getEndDatum()));
				}
				pstmt.executeUpdate();
				rs = pstmt.getGeneratedKeys();
				if(rs != null && rs.next()){
					v = new VerlagDAO().findById(rs.getInt(1));
				}
				
			}
	  catch (SQLException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
     } finally{
         try{
             if(rs != null) rs.close();
             if(pstmt != null) pstmt.close();
             if(conn != null) conn.close();
         } catch(Exception ex){}
     }
			
		return v;
	}

	@Override
	public Verlag update(Verlag domainObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(Verlag domainObject) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Verlag> getSelektion(Verlag domainObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Verlag findById(int id) {
		Verlag v = new Verlag();
		String sql = "SELECT "
				+ "id, "
				+ "name, "
				+ "gruendungsdatum, "
				+ "enddatum "
				+ "FROM verlag "
				+ "WHERE id = ?";
			try {
				
				conn = dbConnection.getDBConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1,id);
				mRS = pstmt.executeQuery();
				while(mRS.next()) {
					 v.setId(mRS.getInt(1));
					 v.setName(mRS.getString(2));
					 v.setGruendungsDatum(mRS.getDate(3));
					 v.setEndDatum(mRS.getDate(4));
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
			
			return v;
	}
	

	@Override
	public List<Verlag> findAll() {
		String sql = "SELECT "
				+ "id, "
				+ "name, "
				+ "gruendungsdatum, "
				+ "enddatum "
				+ "FROM verlag";
			try {
				
				conn = dbConnection.getDBConnection();
				pstmt = conn.prepareStatement(sql);
				mRS = pstmt.executeQuery();
				while(mRS.next()) {
					 Verlag v = new Verlag();
					 v.setId(mRS.getInt(1));
					 v.setName(mRS.getString(2));
					 v.setGruendungsDatum(mRS.getDate(3));
					 v.setEndDatum(mRS.getDate(4));
					 verlagListe.add(v);}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}

			finally {
						try {
							dbConnection.closeConnection(conn);
						} catch (SQLException e) {
							e.printStackTrace();
						}
			
			}
			
			return verlagListe;
	}

}

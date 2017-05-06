package model.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AdapterDAO {
	
	public void dbSave(String xml, String name, String pattern){
		Connection connection = SQLConnection.getInstance().getConnection();
		String sql = "UPDATE adapter SET xml = ? "
                + "WHERE name = ? AND pattern = ?";	
		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {	
			pstmt.setString(1, xml);
			pstmt.setString(2, name);
			pstmt.setString(3, pattern);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(sql);
        	System.out.println(e.getMessage());
		} finally {
			try {
				if (connection != null){
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}	
	}
	
	public void dbInsert(String name, String pattern, String xml){
		String sql = "INSERT INTO adapter(name,pattern,xml) VALUES(?,?,?)";
		Connection connection = SQLConnection.getInstance().getConnection();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, pattern);
            pstmt.setString(3, xml);
            pstmt.executeUpdate();
        } catch (SQLException e) {
        	System.out.println(sql);
            System.out.println(e.getMessage());
        } finally {
        	try {
        		if (connection != null){
        			connection.close();
        		}
			} catch (SQLException e) {
				e.printStackTrace();
			}
        }
	}	

	public void dbDelete(String pattern, String name) {
		String sql = "DELETE FROM adapter WHERE pattern = ? AND name = ?";
		Connection connection = SQLConnection.getInstance().getConnection();
		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setString(1, pattern);
			pstmt.setString(2, name);
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println(sql);
			System.out.println(e.getMessage());
		} finally {
			try {
				if (connection != null){
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}

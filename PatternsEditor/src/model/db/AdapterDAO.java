package model.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdapterDAO {
	
	public void dbSave(String name, String pattern, String xml){
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
	
	public List<String> getAdapters(String pattern){
		List<String> result = new ArrayList<>();
		String sqlAda = "SELECT name FROM adapter WHERE pattern = ?";
		Connection connection = SQLConnection.getInstance().getConnection();
        try (PreparedStatement pstmtAda = connection.prepareStatement(sqlAda)) {
        	connection.setAutoCommit(false);
           
            pstmtAda.setString(1, pattern);
            ResultSet rs2 = pstmtAda.executeQuery();
            while (rs2.next()){
            	result.add(rs2.getString("name"));
            }
        } catch (SQLException e) {
        	System.out.println(sqlAda);
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
        return result;
	}
}

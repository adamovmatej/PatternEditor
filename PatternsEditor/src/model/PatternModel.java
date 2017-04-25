package model;

import java.beans.PropertyChangeListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.event.SwingPropertyChangeSupport;
import javax.swing.table.DefaultTableModel;

import model.db.SQLConnection;

public class PatternModel {
	
	private SwingPropertyChangeSupport propertyChangeSupport;
	
	private List<String> patterns;
	
	public PatternModel(){
		propertyChangeSupport = new SwingPropertyChangeSupport(this);
		this.patterns = dbGetAllPatterns();
	}
	
	public void createPattern(String name, String description){
		patterns.add(name);
		dbInsert(name, description);
	}

	public List<String> getPatterns() {
		return patterns;
	}

	public void setPatterns(List<String> patterns) {
		this.patterns = patterns;
	}
	
	public void addListener(PropertyChangeListener prop) {
		propertyChangeSupport.addPropertyChangeListener(prop);
    }
	
	private List<String> dbGetAllPatterns(){
		List<String> list = new ArrayList<String>();
		String sql = "SELECT name FROM pattern";
		try (Connection connection = SQLConnection.getInstance().getConnection(); 
				Statement stmt  = connection.createStatement();
	            ResultSet rs    = stmt.executeQuery(sql)){
	            
	            while (rs.next()) {
	            	list.add(rs.getString("name"));
	            }
		} catch (SQLException e) {
			System.out.println(e.getMessage());
	    }
		return list;
	}
	
	private void dbInsert(String name, String description){
		String sql = "INSERT INTO pattern(name,description) VALUES(?,?)";
		Connection connection = SQLConnection.getInstance().getConnection();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, description);
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
	
	public DefaultTableModel generateTableModel(){
		DefaultTableModel tableModel = new DefaultTableModel(new Object[]{"Organizacne vzory"}, 0);
		for (String string : patterns) {
			tableModel.addRow(new Object[]{string});
		}
		return tableModel;
	}

	public Pattern dbGetPattern(String name) {
		String sql = "SELECT name, description FROM pattern WHERE name = ?";
		Connection connection = SQLConnection.getInstance().getConnection();
		Pattern pattern = null;
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()){
            	pattern = new Pattern(rs.getString("name"), rs.getString("description"));
            }
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
		return pattern;
	}

	public void updatePattern(String name, String newName, String description) {
		patterns.set(patterns.indexOf(name), newName);
		dbUpdatePattern(name, newName, description);
	}
	
	private void dbUpdatePattern(String name, String newName, String description){
		 String sql = "UPDATE pattern SET name = ? , "
	                + "description = ? "
	                + "WHERE name = ?";
		Connection connection = SQLConnection.getInstance().getConnection();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, newName);
            pstmt.setString(2, description);
            pstmt.setString(3, name);
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
        propertyChangeSupport.firePropertyChange("patternUpdate", name, new Pattern(newName, description));
	}
}

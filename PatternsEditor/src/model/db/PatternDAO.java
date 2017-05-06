package model.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;

import model.Adapter;
import model.Diagram;
import model.PatternObject;

public class PatternDAO {

	public List<String> dbGetAllPatterns(){
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
	
	public void dbInsert(String name, String description){
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

	public PatternObject dbGetPattern(String name) {
		String sql = "SELECT name, description FROM pattern WHERE name = ?";
		Connection connection = SQLConnection.getInstance().getConnection();
		PatternObject pattern = null;
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()){
            	pattern = new PatternObject(rs.getString("name"), rs.getString("description"));
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
	
	public List<String> dbGetNoModelPatterns() {
		String sql = "SELECT name FROM pattern WHERE xml IS NULL";
		Connection connection = SQLConnection.getInstance().getConnection();
		List<String> patterns = new ArrayList<>();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
            	patterns.add(rs.getString("name"));
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
		return patterns;
	}
	
	public List<String> dbGetWithModelPatterns() {
		String sql = "SELECT name FROM pattern WHERE xml IS NOT NULL";
		Connection connection = SQLConnection.getInstance().getConnection();
		List<String> patterns = new ArrayList<>();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
            	patterns.add(rs.getString("name"));
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
		return patterns;
	}
	
	public void dbUpdatePattern(String name, String description){
		String sql = "UPDATE pattern SET description = ? "
	                + "WHERE name = ?";
		Connection connection = SQLConnection.getInstance().getConnection();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, description);
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
	
	public void dbDelete(String name) {
		String sql = "DELETE FROM pattern WHERE name = ?";
		Connection connection = SQLConnection.getInstance().getConnection();
		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setString(1, name);
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
	
	public mxCell dbGetDefaulDiagram(String name, Diagram diagram, mxCell lastCell){
		Connection connection = SQLConnection.getInstance().getConnection();
		String sql = "SELECT xml "
				+ "FROM pattern "
                + "WHERE name = ?";	
		mxCell c = null;
		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {	
			pstmt.setString(1, name);
			
			ResultSet rs = pstmt.executeQuery();
			String xml = null;
			if (rs.next()){
				xml = rs.getString("xml");
				if (xml!=null){
					c = diagram.xmlCloneIn(xml, name, lastCell);
				}
			}
			if (xml == null){
				diagram.getGraph().getModel().beginUpdate();
				try{
					mxGeometry geo = lastCell.getGeometry();
					Double x = geo.getCenterX() + geo.getWidth()/2 + 20;
					c = (mxCell) diagram.getGraph().insertVertex(diagram.getGraph().getDefaultParent(), null, name, x.intValue(), 200, 300, 50);
					c.setStyle("PARENT");
					c.setConnectable(false);
					mxGeometry g = c.getGeometry();
					g.setHeight(g.getHeight()+42);
					g.setWidth(g.getWidth()+42);
					c.setGeometry(g);
				} finally{
					diagram.getGraph().getModel().endUpdate();
				}
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
		return c;
	}
	
	public void dbSaveDefault(String xml, String pattern) {		
		Connection connection = SQLConnection.getInstance().getConnection();
		String sql = "UPDATE pattern SET xml = ? "
                + "WHERE name = ?";	
		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {	
			pstmt.setString(1, xml);
			pstmt.setString(2, pattern);
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
	
	public String dbLoadDefault(String pattern){
		String xml = null;
		String sql = "SELECT xml FROM pattern WHERE name = ?";
		Connection connection = SQLConnection.getInstance().getConnection();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, pattern);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()){
            	xml = rs.getString("xml");
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
        return xml;
	}
	
	public Map<String, Adapter> dbLoadAdapters(String pattern){
		Map<String, Adapter> result = new HashMap<>();
		String sql = "SELECT xml,name FROM adapter WHERE pattern = ?";
		Connection connection = SQLConnection.getInstance().getConnection();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, pattern);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
            	String xml = rs.getString("xml");
            	String name = rs.getString("name");
            	Adapter adapter = new Adapter(pattern, name, xml);
            	result.put(adapter.getLineName(), adapter);
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
        return result;
	}
	
	public String dbLoadAdapter(String pattern, String name){
		String xml = null;
		String sql = "SELECT xml FROM adapter WHERE pattern = ? AND name = ?";
		Connection connection = SQLConnection.getInstance().getConnection();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, pattern);
            pstmt.setString(2, name);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()){
            	xml = rs.getString("xml");
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
        return xml;
	}
	
}

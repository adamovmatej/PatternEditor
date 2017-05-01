package model;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.mxgraph.view.mxGraph;

import model.db.SQLConnection;

public class Adapter implements Serializable{

	private static final long serialVersionUID = 1L;
	private String pattern;
	private List<String> patterns;	
	private Diagram diagram;
	private Boolean isNew;
		
	public Adapter(String pattern, String name, String xml){
		this.pattern = pattern;
		this.diagram = new Diagram(new mxGraph(), pattern);
		this.diagram.xmlIn(xml);
		this.patterns = getPatternList(name);
	}
	
	public Adapter(String pattern, List<String> patterns) {
		this.setPattern(pattern);
		this.isNew = true;
		if (patterns != null){
			this.setPatterns(patterns);
		}
		diagram = new Diagram(new mxGraph(), pattern);
	}
	
	public Adapter(String pattern, List<String> patterns, Diagram diagram) {
		this.setPattern(pattern);
		this.isNew = true;
		if (patterns != null){
			this.setPatterns(patterns);
		}
		this.diagram = diagram;
	}
	
	public String getLineName(){
		String name = "<html>";
		for (String string : patterns) {
			name += string + "<br>";
		}
		return name.substring(0, name.length()-4)+"</html>";
	}

	public String getName() {
		String name = "";
		for (String string : patterns) {
			name += string + " / ";
		}
		return name.substring(0, name.lastIndexOf("/")-1);
	}
	
	public List<String> getPatternList(String name){
		List<String> items = Arrays.asList(name.split("\\s*/\\s*"));
		return new ArrayList<>(items);
	}
	
	public void save(){
		Connection connection = SQLConnection.getInstance().getConnection();
		String sql = "UPDATE adapter SET xml = ? "
                + "WHERE name = ?";	
		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {	
			pstmt.setString(1, diagram.getXml());
			pstmt.setString(2, getLineName());
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
	
	public void insert(){
		String sql = "INSERT INTO adapter(name,pattern,xml) VALUES(?,?,?)";
		Connection connection = SQLConnection.getInstance().getConnection();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, getName());
            pstmt.setString(2, pattern);
            pstmt.setString(3, diagram.getXml());
            pstmt.executeUpdate();
            setIsNew(false);
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

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public List<String> getPatterns() {
		return patterns;
	}

	public void setPatterns(List<String> patterns) {
		this.patterns = patterns;
	}

	public Diagram getDiagram() {
		return diagram;
	}

	public void setDiagram(Diagram diagram) {
		this.diagram = diagram;
	}

	public Boolean getIsNew() {
		return isNew;
	}

	public void setIsNew(Boolean isNew) {
		this.isNew = isNew;
	}
}

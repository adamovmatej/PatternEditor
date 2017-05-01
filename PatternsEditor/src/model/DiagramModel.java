package model;

import java.beans.PropertyChangeListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.event.SwingPropertyChangeSupport;

import com.mxgraph.model.mxCell;
import com.mxgraph.view.mxGraph;

import model.db.SQLConnection;

public class DiagramModel {
	
	private SwingPropertyChangeSupport propertyChangeSupport;	
	private Map<String, Adapter> adapters;	
	private Adapter currentAdapter;
	private String pattern;
	
	public DiagramModel(){		
	}
	
	public DiagramModel(String pattern) {
		this.setPattern(pattern);
		propertyChangeSupport = new SwingPropertyChangeSupport(this);
		List<String> list = new ArrayList<>();
		list.add("Default");
		currentAdapter = new Adapter(pattern, list);
		adapters = new HashMap<String, Adapter>();
		adapters.put(currentAdapter.getLineName(), currentAdapter);
	}
	
	public void load(String name){
		loadDefault();
		loadAdapters();
	}

	public void createAdapter(List<String> patterns){
		mxGraph graph = new mxGraph();
		mxGraph def = adapters.get("<html>Default</html>").getDiagram().getGraph();
		mxCell cell;
		graph.getModel().beginUpdate();
		try{
			cell = (mxCell) graph.insertVertex(graph.getDefaultParent(), null, "Default", 30, 30, 100, 50);
			cell.setStyle("PARENT");
			graph.addCells(def.cloneCells(def.getChildCells(def.getDefaultParent())), cell);
		} finally{
			graph.getModel().endUpdate();
		}
		Diagram diagram = new Diagram(graph, pattern);
		getDefaulDiagrams(patterns, diagram);
		currentAdapter = new Adapter(pattern, patterns, diagram);
		adapters.put(currentAdapter.getLineName(), currentAdapter);
	}
	
	private void getDefaulDiagrams(List<String> patterns,  Diagram diagram){
		String list = "";
		for (String pattern : patterns) {
			if (!getDefaulDiagram(pattern, diagram)){
				list+=pattern+", ";
			}
		}
		if (!list.isEmpty()){
			JOptionPane.showMessageDialog(diagram, "Patterns: "+list.substring(0, list.length()-2)+" dont have default diagrams!");			
		}
	}
	
	private boolean getDefaulDiagram(String name, Diagram diagram){
		Boolean result = true;
		Connection connection = SQLConnection.getInstance().getConnection();
		String sql = "SELECT xml "
				+ "FROM pattern "
                + "WHERE name = ?";	
		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {	
			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()){
				String xml = rs.getString("xml");
				diagram.xmlCloneIn(xml, name);
				if (xml==null){
					result = false;
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
		return result;
	}
	
	public void saveDefault() {		
		Connection connection = SQLConnection.getInstance().getConnection();
		String sql = "UPDATE pattern SET xml = ? "
                + "WHERE name = ?";	
		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {	
			pstmt.setString(1, adapters.get("<html>Default</html>").getDiagram().getXml());
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
	
	public void loadDefault(){
		String sql = "SELECT xml FROM pattern WHERE name = ?";
		Connection connection = SQLConnection.getInstance().getConnection();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, pattern);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()){
            	String xml = rs.getString("xml");
            	currentAdapter.getDiagram().xmlIn(xml);
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
	}	

	private void loadAdapters() {
		String sql = "SELECT xml,name FROM adapter WHERE pattern = ?";
		Connection connection = SQLConnection.getInstance().getConnection();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, pattern);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
            	String xml = rs.getString("xml");
            	String name = rs.getString("name");
            	Adapter adapter = new Adapter(pattern, name, xml);
            	adapters.put(adapter.getLineName(), adapter);
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
	}
	
	public void loadAdapter(String name){
		String sql = "SELECT xml FROM adapter WHERE pattern = ? AND name = ?";
		Connection connection = SQLConnection.getInstance().getConnection();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, pattern);
            pstmt.setString(2, name);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()){
            	currentAdapter = new Adapter(pattern, name, rs.getString("xml"));
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
	}
	
	public void saveAll() {
		saveDefault();
		Adapter adapter;
		for (String key : adapters.keySet()) {
			adapter = adapters.get(key);
			if (!key.equals("<html>Default</html>")){
				if (adapter.getIsNew()){
					adapter.insert();
				} else {
					adapter.save();				
				}
			}
		}
	}

	public void addListener(PropertyChangeListener prop){
		propertyChangeSupport.addPropertyChangeListener(prop);
	}
	
	public void removeVersion(String pattern, int selection) {	
		//TODO
		//VariationModel varModel = getVariationModel(pattern);
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public Adapter getCurrentAdapter() {
		return currentAdapter;
	}	

	public Adapter getAdapter(String key) {
		return adapters.get(key);
	}

	public void setCurrentAdapter(Adapter currentAdapter) {
		this.currentAdapter = currentAdapter;
	}

	public Map<String, Adapter> getAdapters() {
		return adapters;
	}

	public void setAdapters(Map<String, Adapter> adapters) {
		this.adapters = adapters;
	}

	public void changeAdapter(String adapter) {
		if (adapter == null){
			adapter = "<html>Default</html>";
		}
		System.out.println(adapter);
		currentAdapter = adapters.get(adapter);
		propertyChangeSupport.firePropertyChange("adapterChange", null, currentAdapter.getDiagram());
	}
}

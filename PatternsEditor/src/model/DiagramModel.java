package model;

import java.beans.PropertyChangeListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.event.SwingPropertyChangeSupport;

import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
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

	public void createAdapter(Adapter adapter){
		mxGraph graph = new mxGraph();
		Diagram diagram = new Diagram(graph, pattern);
		mxGraph def = adapters.get("<html>Default</html>").getDiagram().getGraph();
		mxCell cell;
		graph.getModel().beginUpdate();
		try{
			cell = (mxCell) graph.insertVertex(graph.getDefaultParent(), null, "Default", 30, 30, 0, 0);
			cell.setStyle("PARENT");
			cell.setConnectable(false);
			graph.addCells(def.cloneCells(def.getChildCells(def.getDefaultParent())), cell);
			mxGeometry g = cell.getGeometry();
			g.setHeight(g.getHeight()+42);
			g.setWidth(g.getWidth()+42);
			cell.setGeometry(g);
		} finally{
			graph.getModel().endUpdate();
		}
		getDefaulDiagrams(adapter.getPatterns(), diagram, cell);
		adapter.setDiagram(diagram);
		currentAdapter = adapter;
		adapters.put(currentAdapter.getLineName(), currentAdapter);
	}
	
	private void getDefaulDiagrams(List<String> patterns,  Diagram diagram, mxCell cell){
		mxCell lastCell = cell;
		for (String pattern : patterns) {
			mxCell c = getDefaulDiagram(pattern, diagram, lastCell);
			if (c != null){
				lastCell = c;
			}
		}
	}
	
	private mxCell getDefaulDiagram(String name, Diagram diagram, mxCell lastCell){
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

	public void changeAdapter(String adapter) {
		if (adapter == null){
			adapter = "<html>Default</html>";
		}
		System.out.println(adapter);
		currentAdapter = adapters.get(adapter);
		propertyChangeSupport.firePropertyChange("adapterChange", null, currentAdapter.getDiagram());
	}
	
	public void deleteAdapter(String adapter) {
		adapters.remove(adapter);
	}
	
	public void addListener(PropertyChangeListener prop){
		propertyChangeSupport.addPropertyChangeListener(prop);
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

}

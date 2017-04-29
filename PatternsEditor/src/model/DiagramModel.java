package model;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.event.SwingPropertyChangeSupport;

public class DiagramModel {
	
	private SwingPropertyChangeSupport propertyChangeSupport;	
	private Map<String, Adapter> adapters;	
	private Adapter currentAdapter;
	private String pattern;
	
	public DiagramModel(String pattern) {
		this.setPattern(pattern);
		propertyChangeSupport = new SwingPropertyChangeSupport(this);
		List<String> list = new ArrayList<>();
		list.add("Default");
		currentAdapter = new Adapter(pattern, list);
		adapters = new HashMap<String, Adapter>();
		adapters.put(currentAdapter.getLineName(), currentAdapter);
	}
	
	public void createAdapter(List<String> patterns){
		currentAdapter = new Adapter(pattern, patterns);
		adapters.put(currentAdapter.getLineName(), currentAdapter);
		propertyChangeSupport.firePropertyChange("newAdapter", null, currentAdapter);
	}
	
	public void save() {
		/*
		Connection connection = SQLConnection.getInstance().getConnection();
		String sql = "UPDATE pattern SET xml = ? "
                + "WHERE name = ?";	
		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {	
			pstmt.setString(1, currentDiagram.getXml());
			pstmt.setString(2, currentDiagram.getPattern());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(sql);
        	System.out.println(e.getMessage());
		} finally {
			try {
				if (connection != null){
					connection.close();
				}firePropertyChange
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		*/
		//TODO
		//propertyChangeSupport.firePropertyChange("patternUpdate", name, new Pattern(newName, description));
	}
	
	public void saveAll() {
		//TODO
	}
	
	public void open(String pattern){
		/*
		String sql = "SELECT xml FROM pattern WHERE name = ?";
		Connection connection = SQLConnection.getInstance().getConnection();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, pattern);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()){
            	String xml = rs.getString("xml");
            	VariationModel variationModel = new VariationModel(pattern, connection);
            	variationModels.put(pattern, variationModel);
            	currentDiagram = new Diagram(new mxGraph(), pattern, variationModel);
            	currentDiagram.xmlIn(xml);
            	diagrams.put(pattern, currentDiagram);
        		propertyChangeSupport.firePropertyChange("newDiagram", variationModel, currentDiagram);
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
        */
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
		System.out.println("Change : "+adapter);
		currentAdapter = adapters.get(adapter);
		propertyChangeSupport.firePropertyChange("adapterChange", null, currentAdapter.getDiagram());
	}
}

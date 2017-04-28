package model;

import java.beans.PropertyChangeListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.event.SwingPropertyChangeSupport;

import com.mxgraph.view.mxGraph;

import model.db.SQLConnection;

public class DiagramModel {
	
	private SwingPropertyChangeSupport propertyChangeSupport;
	
	private Map<String, Diagram> diagrams;
	private Map<String, VariationModel> variationModels;
	private Diagram currentDiagram;
	
	public DiagramModel() {
		propertyChangeSupport = new SwingPropertyChangeSupport(this);
		diagrams = new HashMap<String, Diagram>();
		variationModels = new HashMap<String, VariationModel>();
	}
	
	public void createDiagram(String name){
		currentDiagram = new Diagram(new mxGraph(), name, null);
		diagrams.put(name, currentDiagram);
		VariationModel variationModel = new VariationModel(currentDiagram);
		variationModels.put(name, variationModel);
		propertyChangeSupport.firePropertyChange("newDiagram", variationModel, currentDiagram);
	}
	
	public void createVersion(String name) {
		Version version = new Version(currentDiagram.getPattern(), name);
		currentDiagram.addVersion(version);
		VariationModel variationModel = getVariationModel(version.getMainPattern());
		variationModel.addVariation(version);
		propertyChangeSupport.firePropertyChange("newVariation", null, version);
	}
	
	public void createAdapter(String name) {
		Adapter adapter = new Adapter(currentDiagram.getPattern(), name);
		currentDiagram.addAdapter(adapter);
		VariationModel variationModel = getVariationModel(adapter.getMainPattern());
		variationModel.addVariation(adapter);
		propertyChangeSupport.firePropertyChange("newVariation", null, adapter);
	}
	
	public void changeVersion(String name){
		currentDiagram.changeVersion(name);		
		propertyChangeSupport.firePropertyChange("variationChange", null, currentDiagram.getCurrentVariation());
	}
	
	public void changeAdapter(String name){
		currentDiagram.changeAdapter(name);		
		propertyChangeSupport.firePropertyChange("variationChange", null, currentDiagram.getCurrentVariation());
	}
	
	public void addListener(PropertyChangeListener prop) {
		propertyChangeSupport.addPropertyChangeListener(prop);
    }
	
	public Diagram getDiagram(String pattern){
		return diagrams.get(pattern);
	}

	public Map<String, Diagram> getDiagrams() {
		return diagrams;
	}

	public void setDiagrams(Map<String, Diagram> diagrams) {
		this.diagrams = diagrams;
	}
	
	public VariationModel getVariationModel(String name){
		return variationModels.get(name);
	}

	public Map<String, VariationModel> getVersionModels() {
		return variationModels;
	}

	public void setVersionModels(Map<String, VariationModel> versionModels) {
		this.variationModels = versionModels;
	}

	public Diagram getCurrentDiagram() {
		return currentDiagram;
	}

	public void setCurrentDiagram(Diagram currentDiagram) {
		if (currentDiagram == this.currentDiagram){
			return;
		}
		this.currentDiagram = currentDiagram;
		VariationModel model = null;
		if (currentDiagram != null){
			model = variationModels.get(currentDiagram.getPattern());
		}
		propertyChangeSupport.firePropertyChange("changeDiagram", currentDiagram, model);
	}

	public void closeDiagram(Diagram diagram) {
		diagrams.remove(diagram.getPattern());
	}
	
	public void save() {
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
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		//TODO
		//propertyChangeSupport.firePropertyChange("patternUpdate", name, new Pattern(newName, description));
	}
	
	public void saveAll() {
		//TODO
	}
	
	public void open(String pattern){
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
	}

	public void removeVersion(String pattern, int selection) {		
		VariationModel varModel = getVariationModel(pattern);
		varModel.getMainTableModel().removeRow(selection);
	}
}

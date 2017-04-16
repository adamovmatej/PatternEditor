package model;

import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.event.SwingPropertyChangeSupport;

import com.mxgraph.view.mxGraph;

public class DiagramModel {
	
	private SwingPropertyChangeSupport propertyChangeSupport;
	
	private Map<String, Diagram> diagrams;
	private Map<String, VersionModel> versionModels;
	private Diagram currentDiagram;
	
	public DiagramModel() {
		propertyChangeSupport = new SwingPropertyChangeSupport(this);
		diagrams = new HashMap<String, Diagram>();
		versionModels = new HashMap<String, VersionModel>();
	}
	
	public void createDiagram(String name, String version, Boolean main){
		currentDiagram = new Diagram(new mxGraph(), name, version, main);
		diagrams.put(name, currentDiagram);
		VersionModel versionModel = new VersionModel(currentDiagram);
		versionModels.put(name, versionModel);
		propertyChangeSupport.firePropertyChange("newDiagram", versionModel, currentDiagram);
	}
	
	public void createVersion(String name, String version, Boolean main) {
		Version ver = new Version(name, version, main);
		getDiagram(name).createVersion(ver);
		VersionModel versionModel = getVersionModel(name);
		versionModel.addVersion(ver);
		propertyChangeSupport.firePropertyChange("newVersion", null, ver);
	}
	
	public void changeVersion(String name){
		currentDiagram.changeVersion(name);		
		propertyChangeSupport.firePropertyChange("versionChange", null, currentDiagram.getCurrentVersion());
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
	
	private VersionModel getVersionModel(String name){
		return versionModels.get(name);
	}

	public Map<String, VersionModel> getVersionModels() {
		return versionModels;
	}

	public void setVersionModels(Map<String, VersionModel> versionModels) {
		this.versionModels = versionModels;
	}

	public Diagram getCurrentDiagram() {
		return currentDiagram;
	}

	public void setCurrentDiagram(Diagram currentDiagram) {
		if (currentDiagram == this.currentDiagram){
			return;
		}
		this.currentDiagram = currentDiagram;
		propertyChangeSupport.firePropertyChange("changeDiagram", null, versionModels.get(currentDiagram.getPattern()));
	}
}

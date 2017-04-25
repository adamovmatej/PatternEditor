package model;

import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.event.SwingPropertyChangeSupport;

import com.mxgraph.view.mxGraph;

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
		currentDiagram = new Diagram(new mxGraph(), name);
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
	
	private VariationModel getVariationModel(String name){
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
		propertyChangeSupport.firePropertyChange("changeDiagram", null, variationModels.get(currentDiagram.getPattern()));
	}
}

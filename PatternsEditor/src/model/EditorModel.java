package model;

import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.event.SwingPropertyChangeSupport;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

public class EditorModel {
	
	private SwingPropertyChangeSupport propertyChangeSupport;
	private Map<String, DiagramModel> diagramModels;
	private Map<String, AdapterModel> adapterModels;
	private DiagramModel currentDiagramModel;
	private AdapterModel currentAdapterModel;
	private int tool;
	
	public EditorModel() {
		tool = 0;
		diagramModels = new HashMap<String, DiagramModel>();
		adapterModels = new HashMap<String, AdapterModel>();
		propertyChangeSupport = new SwingPropertyChangeSupport(this);
	}
	
	public void createDiagramModel(String name){
		currentDiagramModel = new DiagramModel(name);
		diagramModels.put(name, currentDiagramModel);
		currentAdapterModel = new AdapterModel();
		initAdapterModelListeners(currentAdapterModel);
		currentAdapterModel.initTables(currentDiagramModel);
		adapterModels.put(name, currentAdapterModel);
		propertyChangeSupport.firePropertyChange("newDiagramModel", currentAdapterModel, currentDiagramModel);
	}

	public void changeDiagramModel(String name) {
		if (!currentDiagramModel.getCurrentAdapter().getPattern().equals(name)){
			currentAdapterModel = adapterModels.get(name);
			currentDiagramModel = diagramModels.get(name);
			propertyChangeSupport.firePropertyChange("diagramModelChange", currentAdapterModel, currentDiagramModel);			
		}
	}
	
	public void createAdapter(List<String> patterns) {
		currentDiagramModel.createAdapter(patterns);
		currentAdapterModel.addAdapter(currentDiagramModel.getCurrentAdapter().getLineName());
		propertyChangeSupport.firePropertyChange("newAdapter", null, currentDiagramModel.getCurrentAdapter());
	}	

	public void closeDiagramModel(String pattern) {
		diagramModels.remove(pattern);
		adapterModels.remove(pattern);
	}
	
	public void addListener(PropertyChangeListener prop){
		propertyChangeSupport.addPropertyChangeListener(prop);
	}

	public Map<String, AdapterModel> getAdapterModels() {
		return adapterModels;
	}

	public void setAdapterModels(Map<String, AdapterModel> adapterModels) {
		this.adapterModels = adapterModels;
	}

	public AdapterModel getAdapterModel(String pattern) {
		return adapterModels.get(pattern);
	}
	
	public Map<String, DiagramModel> getDiagramModels() {
		return diagramModels;
	}

	public void setDiagramModels(Map<String, DiagramModel> diagramModels) {
		this.diagramModels = diagramModels;
	}

	public DiagramModel getCurrentDiagramModel() {
		return currentDiagramModel;
	}

	public void setCurrentDiagramModel(DiagramModel currentDiagramModel) {
		this.currentDiagramModel = currentDiagramModel;
	}

	public AdapterModel getCurrentAdapterModel() {
		return currentAdapterModel;
	}

	public void setCurrentAdapterModel(AdapterModel currentAdapterModel) {
		this.currentAdapterModel = currentAdapterModel;
	}

	public void save() {
		currentDiagramModel.saveAll();
	}
	
	public void open(String name) {
		currentDiagramModel = new DiagramModel(name);
		currentDiagramModel.load(name);
		diagramModels.put(name, currentDiagramModel);
		currentAdapterModel = new AdapterModel();
		initAdapterModelListeners(currentAdapterModel);
		currentAdapterModel.initTables(currentDiagramModel);
		adapterModels.put(name, currentAdapterModel);
		propertyChangeSupport.firePropertyChange("newDiagramModel", currentAdapterModel, currentDiagramModel);
	}
	
	public void loadDiagram(String pattern, String name){
		DiagramModel model = new DiagramModel(pattern);
		model.setPattern(pattern);
		if (name.equals("Default")){
			model.loadDefault();
		} else {
			model.loadAdapter(name);
		}
		propertyChangeSupport.firePropertyChange("playDiagram", null, model);
	}
	
	private void initAdapterModelListeners(AdapterModel model){
		model.addTableModelListener(new TableModelListener() {			
			@Override
			public void tableChanged(TableModelEvent e) {
				if (e.getType() == TableModelEvent.INSERT){
					propertyChangeSupport.firePropertyChange("tableChange", null, null);					
				}
			}
		});
	}

	public void saveAll() {
		for (String key: diagramModels.keySet()) {
			diagramModels.get(key).saveAll();
		}
	}

	public int getTool() {
		return tool;
	}

	public void setTool(int tool) {
		this.tool = tool;
	}
}

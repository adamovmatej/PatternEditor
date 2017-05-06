package model;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import javax.swing.event.SwingPropertyChangeSupport;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import com.mxgraph.model.mxCell;
import com.mxgraph.view.mxGraph;

import model.db.AdapterDAO;
import model.db.DAOFactory;

public class EditorModel {
	
	private SwingPropertyChangeSupport propertyChangeSupport;
	private Map<String, PatternModel> patternModels;
	private Map<String, AdapterModel> adapterModels;
	private PatternModel currentDiagramModel;
	private AdapterModel currentAdapterModel;
	private AdapterDAO adapterDAO;
	private int tool;
	
	public EditorModel() {
		tool = 0;
		adapterDAO = DAOFactory.getInstance().getAdapterDAO();
		patternModels = new HashMap<String, PatternModel>();
		adapterModels = new HashMap<String, AdapterModel>();
		propertyChangeSupport = new SwingPropertyChangeSupport(this);
	}
	
	public void createDiagramModel(String name){
		currentDiagramModel = new PatternModel(name);
		patternModels.put(name, currentDiagramModel);
		currentAdapterModel = new AdapterModel();
		initAdapterModelListeners(currentAdapterModel);
		currentAdapterModel.initTables(currentDiagramModel);
		adapterModels.put(name, currentAdapterModel);
		propertyChangeSupport.firePropertyChange("newDiagramModel", currentAdapterModel, currentDiagramModel);
	}

	public void changeDiagramModel(String name) {
		if (!currentDiagramModel.getCurrentAdapter().getPattern().equals(name)){
			currentAdapterModel = adapterModels.get(name);
			currentDiagramModel = patternModels.get(name);
			propertyChangeSupport.firePropertyChange("diagramModelChange", currentAdapterModel, currentDiagramModel);			
		}
	}
	
	public void createAdapter(List<String> patterns) {
		Adapter adapter = new Adapter(currentDiagramModel.getPattern(), patterns, null);
		if (currentDiagramModel.getAdapter(adapter.getLineName())==null){
			currentDiagramModel.createAdapter(adapter);
			currentAdapterModel.addAdapter(currentDiagramModel.getCurrentAdapter().getLineName());
			propertyChangeSupport.firePropertyChange("newAdapter", null, currentDiagramModel.getCurrentAdapter());			
		} else {
			propertyChangeSupport.firePropertyChange("newAdapter", null, null);			
		}
	}

	public void closeDiagramModel(String pattern) {
		patternModels.remove(pattern);
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
	
	public Map<String, PatternModel> getDiagramModels() {
		return patternModels;
	}

	public void setDiagramModels(Map<String, PatternModel> diagramModels) {
		this.patternModels = diagramModels;
	}

	public PatternModel getCurrentDiagramModel() {
		return currentDiagramModel;
	}

	public void setCurrentDiagramModel(PatternModel currentDiagramModel) {
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
		currentDiagramModel = new PatternModel(name);
		currentDiagramModel.load(name);
		patternModels.put(name, currentDiagramModel);
		currentAdapterModel = new AdapterModel();
		initAdapterModelListeners(currentAdapterModel);
		currentAdapterModel.initTables(currentDiagramModel);
		adapterModels.put(name, currentAdapterModel);
		propertyChangeSupport.firePropertyChange("newDiagramModel", currentAdapterModel, currentDiagramModel);
	}
	
	public void loadDiagram(String pattern, String name){
		PatternModel model = new PatternModel(pattern);
		model.setPattern(pattern);
		if (name.equals("Default")){
			model.loadDefault();
		} else {
			model.loadAdapter(name);
		}
		if (checkDiagram(model)){
			propertyChangeSupport.firePropertyChange("playDiagram", null, model);			
		} else {
			propertyChangeSupport.firePropertyChange("playDiagram", null, null);
		}
	}
	
	private boolean checkDiagram(PatternModel model) {
		mxCell start=null;
		mxCell end=null;
		Boolean endReached = false;
		mxGraph graph = model.getCurrentAdapter().getDiagram().getGraph();
		List<Object> cells = new ArrayList<Object>(Arrays.asList(graph.getChildCells(graph.getDefaultParent())));
		for (int i = 0; i < cells.size(); i++){
			mxCell current = (mxCell) cells.get(i);
			cells.addAll(Arrays.asList(graph.getChildCells(current, true, true)));
			if (current.getValue().equals("Start")){
				start = current;
				if (end != null){
					break;
				}
			} else if (current.getValue().equals("End")) {
				end = current;
				if (start != null){
					break;
				}
			}
		}		
		if (start == null || end == null){
			System.out.println("No start or end");
			return false;
		}
		Map<mxCell, Boolean> map = new HashMap<>();
		Stack<mxCell> stack = new Stack<>();
		stack.clear();
		
		map.put(start, true);
		Boolean allDisabled = true;
		for (Object edge : graph.getOutgoingEdges(start)) {
			Edge e = (Edge)(((mxCell)edge).getValue());
			if (!e.getDisabled()){
				allDisabled = false;
				stack.push((mxCell) ((mxCell)edge).getTarget());															
			}
		}
		if (allDisabled){
			System.out.println("All edges disabled");
			return false;
		}
		
		while (!stack.isEmpty()){
			mxCell c = (mxCell) stack.pop();
			if (map.get(c) == null){
				map.put(c, true);
				if (c != end){
					CellNode node = (CellNode) c.getValue();
					if (node.getName().isEmpty() || node.getScene().isEmpty()){
						return false;
					}
					List<Object> edges = new ArrayList<>(Arrays.asList(graph.getOutgoingEdges(c)));
					if (edges.size()==0){
						System.out.println("No edges");
						return false;
					} else {
						allDisabled = true;
						for (Object edge : edges) {
							Edge e = (Edge)(((mxCell)edge).getValue());
							if (!e.getDisabled() && ((((mxCell)edge).getTarget()).getValue().getClass().equals(String.class) || !((CellNode)((mxCell)edge).getTarget().getValue()).getDisabled())){
								allDisabled = false;
								if ((e.getName().isEmpty() || e.getScene().isEmpty()) && !((mxCell)edge).getTarget().equals(end)){
									System.out.println("Empty no end edge");
									return false;
								} else {
									stack.push((mxCell) ((mxCell)edge).getTarget());								
								}
							}
						}
						if (allDisabled){
							System.out.println("All edges disabled");
							return false;
						}
					}
				} else {
					System.out.println("End reached");
					endReached = true;
				}
			}
		}
		System.out.println("End "+endReached);
		return endReached;
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
		for (String key: patternModels.keySet()) {
			patternModels.get(key).saveAll();
		}
	}

	public int getTool() {
		return tool;
	}

	public void setTool(int tool) {
		this.tool = tool;
	}

	public void deleteAdapter(String pattern, String name) {
		PatternModel patternModel = patternModels.get(pattern);
		Adapter adapter = new Adapter(pattern, name, null);
		if (patternModel!=null){
			patternModel.deleteAdapter(name);
			if (patternModel.getCurrentAdapter().getName().equals(name)){
				patternModel.changeAdapter(null);
			}		
			AdapterModel adapterModel = adapterModels.get(pattern);
			adapterModel.removeRow(adapter);	
		}
		adapterDAO.dbDelete(adapter.getPattern(), adapter.getName());
	}
}

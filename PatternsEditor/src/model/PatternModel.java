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

import model.db.AdapterDAO;
import model.db.DAOFactory;
import model.db.PatternDAO;
import model.db.SQLConnection;

public class PatternModel {
	
	private SwingPropertyChangeSupport propertyChangeSupport;	
	private Map<String, Adapter> adapters;	
	private Adapter currentAdapter;
	private String pattern;
	private PatternDAO patternDAO;
	private AdapterDAO adapterDAO;
	
	
	public PatternModel(){		
	}
	
	public PatternModel(String pattern) {
		propertyChangeSupport = new SwingPropertyChangeSupport(this);
		this.patternDAO = DAOFactory.getInstance().getPatternDAO();
		this.adapterDAO = DAOFactory.getInstance().getAdapterDAO();
		this.setPattern(pattern);
		
		List<String> list = new ArrayList<>();
		list.add("Default");
		currentAdapter = new Adapter(pattern, list);
		adapters = new HashMap<String, Adapter>();
		adapters.put(currentAdapter.getLineName(), currentAdapter);
		System.out.println(adapters.get("<html>Default</html>"));
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
			List<Object> cells = new ArrayList<Object>(Arrays.asList(graph.getChildCells(cell)));
			for (Object obj : cells) {
				mxCell c = (mxCell)obj;
				if (c.isEdge()){
					Edge value = (Edge) c.getValue();
					c.setValue(new Edge(value.getName(), value.getScene(), value.getDisabled()));
				} else if (!c.getValue().getClass().equals(String.class)){
					State value = (State) c.getValue();
					c.setValue(new State(value.getName(), value.getScene(), value.getDisabled()));
				}
			}
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
			mxCell c = patternDAO.dbGetDefaulDiagram(pattern, diagram, lastCell);
			if (c != null){
				lastCell = c;
			}
		}
	}
	
	public void saveDefault() {		
		patternDAO.dbSaveDefault(adapters.get("<html>Default</html>").getDiagram().getXml(), pattern);		
	}
	
	public void loadDefault(){
		currentAdapter.getDiagram().xmlIn(patternDAO.dbLoadDefault(pattern));
	}	

	private void loadAdapters() {
		Adapter def = adapters.get("<html>Default</html>");
        adapters = patternDAO.dbLoadAdapters(pattern);
        adapters.put("<html>Default</html>", def);
	}
	
	public void loadAdapter(String name){
        currentAdapter = new Adapter(pattern, name, patternDAO.dbLoadAdapter(pattern, name));
	}
	
	public void saveAll() {
		saveDefault();
		Adapter adapter;
		for (String key : adapters.keySet()) {
			adapter = adapters.get(key);
			if (!key.equals("<html>Default</html>")){
				if (adapter.getIsNew()){
					System.out.println(key);
					adapterDAO.dbInsert(adapter.getName(), adapter.getPattern(), adapter.getDiagram().getXml());
					adapter.setIsNew(false);
				} else {
					adapterDAO.dbSave(adapter.getName(), adapter.getPattern(), adapter.getDiagram().getXml());			
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

package model;

import java.beans.PropertyChangeListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.event.SwingPropertyChangeSupport;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import model.db.AdapterDAO;
import model.db.DAOFactory;
import model.db.PatternDAO;
import model.db.SQLConnection;

public class Pattern{
	
	private SwingPropertyChangeSupport propertyChangeSupport;
	
	private List<String> patterns;
	private PatternDAO patternDB;
	
	public Pattern(){
		patternDB = DAOFactory.getInstance().getPatternDAO();
		propertyChangeSupport = new SwingPropertyChangeSupport(this);
		this.patterns = patternDB.dbGetAllPatterns();
	}
	
	public void createPattern(String name, String description){
		if (patterns.contains(name)){
			propertyChangeSupport.firePropertyChange("newPattern", null, null);
		} else {
			patterns.add(name);
			patternDB.dbInsert(name, description);		
		}
	}

	public List<String> getPatterns() {
		return patterns;
	}

	public void setPatterns(List<String> patterns) {
		this.patterns = patterns;
	}
	
	public void addListener(PropertyChangeListener prop) {
		propertyChangeSupport.addPropertyChangeListener(prop);
    }

	public PatternObject getPattern(String name) {
		return patternDB.dbGetPattern(name);
	}
	
	public List<String> getNoModelPatterns() {
		return patternDB.dbGetNoModelPatterns();
	}
	
	public List<String> getWithModelPatterns() {
		return patternDB.dbGetWithModelPatterns();
	}
	
	public void updatePattern(String name, String description){
		patternDB.dbUpdatePattern(name, description);
        propertyChangeSupport.firePropertyChange("patternUpdate", name, new PatternObject(name, description));
	}
	
	public void deletePattern(String name) {
		patterns.remove(patterns.indexOf(name));
		patternDB.dbDelete(name);
	}
	
	public DefaultTableModel generateTableModel(){
		DefaultTableModel tableModel = new DefaultTableModel(new Object[]{"Organizacne vzory"}, 0);
		for (String string : patterns) {
			tableModel.addRow(new Object[]{string});
		}
		return tableModel;
	}

	public TableModel generateNewModelTableModel() {
		DefaultTableModel tableModel = new DefaultTableModel(new Object[]{"Organizacne vzory"}, 0);
		for (String string : getNoModelPatterns()) {
			tableModel.addRow(new Object[]{string});
		}
		return tableModel;
	}

	public TableModel generatOpenTableModel() {
		DefaultTableModel tableModel = new DefaultTableModel(new Object[]{"Organizacne vzory"}, 0);
		for (String string : getWithModelPatterns()) {
			tableModel.addRow(new Object[]{string});
		}
		return tableModel;
	}

	public TableModel generatNewAdapterTableModel(String pattern) {
		DefaultTableModel tableModel = new DefaultTableModel(new Object[]{"Organizacne vzory"}, 0);
		for (String string : patterns) {
			if (!string.equals(pattern)){
				tableModel.addRow(new Object[]{string});				
			}
		}
		return tableModel;
	}
}

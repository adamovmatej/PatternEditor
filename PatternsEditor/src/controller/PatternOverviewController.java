package controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import model.DiagramModel;
import model.EditorModel;
import model.Pattern;
import model.PatternModel;
import model.AdapterModel;
import model.Diagram;
import view.PatternsOverView;

public class PatternOverviewController implements PropertyChangeListener{
	
	private PatternsOverView overview = null;
	private PlayController playController;
	private PatternModel patternModel;
	private EditorModel editorModel;
	
	private String currentPattern;
	
	public PatternOverviewController(PatternModel patternModel, EditorModel editorModel, PlayController playController) {
		this.playController = playController;
		this.patternModel = patternModel;
		this.editorModel = editorModel;
		
		patternModel.addListener(this);
		editorModel.addListener(this);
	}
	
	public void showPatternOverview() {
		if (overview == null){
			overview = new PatternsOverView();
			overview.setController(this);
			initTable();
			overview.setVisible(true);
		} else{
			populateTable();
			overview.setVisible(true);			
		}		
	}
	
	private void populateAdapterTable(String pattern) {
		AdapterModel adapterModel = new AdapterModel();	
		adapterModel.initTables(pattern, null);
		overview.getAdapterTable().setModel(adapterModel);
	}

	private void populateTable(){
		overview.getTable().setModel(patternModel.generateTableModel());
	}
	
	private void initTable(){		
		overview.getTable().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		overview.getTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				int selected = overview.getTable().getSelectedRow();
				if (selected > -1){
					currentPattern = (String) overview.getTable().getModel().getValueAt(selected, 0);
					initPatternInformation(currentPattern);	
				}
			}
		});

		populateTable();
	}
	
	private void initPatternInformation(String name){
		Pattern pattern = patternModel.dbGetPattern(name);
		populateAdapterTable(name);
		overview.showOverview(pattern.getName(), pattern.getDescription());
	}

	public void saveOverview(String name, String description) {
		patternModel.updatePattern(currentPattern, name, description);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("patternUpdate")){
			Pattern pattern = (Pattern) evt.getNewValue();
			updateTable((String) evt.getOldValue(), pattern.getName());
			return;
		}
		if (evt.getPropertyName().equals("playDiagram")){
			Diagram diagram = ((DiagramModel)evt.getNewValue()).getCurrentAdapter().getDiagram();
			playController.showView();
			playController.play(diagram);
			return;
		}
	}
	
	private void updateTable(String oldName, String newName){
		for (int i = 0; i < overview.getTable().getModel().getRowCount(); i++) {
			if (overview.getTable().getValueAt(i, 0).equals(oldName)){
				if (currentPattern.equals(oldName)){
					currentPattern = newName;
				}
				overview.getTable().setValueAt(newName, i, 0);
				return;
			}
		}
	}
	
	public void playDiagram(String adapter){
		editorModel.loadDiagram(currentPattern, adapter);
	}
	
	public void removeVersion(){
		//TODO
		//diagramModel.removeVersion((String) overview.getTable().getModel().getValueAt(overview.getTable().getSelectedRow(), 0), selection);
	}
	
	public void removeAdapter(){
		//TODO
		((DefaultTableModel)overview.getAdapterTable().getModel()).removeRow(overview.getAdapterTable().getSelectedRow());
	}
}

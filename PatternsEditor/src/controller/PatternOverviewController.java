package controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JOptionPane;
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
	private MainScreenController mainController;
	
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
		if (overview.getTable().getRowCount()>-1){
			overview.getTable().setRowSelectionInterval(0, 0);
		}
	}
	
	private void populateAdapterTable(String pattern) {
		AdapterModel adapterModel = new AdapterModel();	
		adapterModel.initTables(pattern, null);
		overview.getAdapterTable().setModel(adapterModel);
		if (overview.getAdapterTable().getRowCount()>-1){
			overview.getAdapterTable().setRowSelectionInterval(0, 0);
		}		
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
					if (overview.getTable().getValueAt(selected, 0).equals("Default")){
						overview.getBtnDelete().setEnabled(false);
					} else {
						overview.getBtnDelete().setEnabled(false);
					}
					currentPattern = (String) overview.getTable().getModel().getValueAt(selected, 0);
					initPatternInformation(currentPattern);	
				}
			}
		});
		
		overview.getAdapterTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				int selected = overview.getAdapterTable().getSelectedRow();
				if (selected > -1){
					System.out.println(overview.getAdapterTable().getValueAt(selected, 0));
					if (overview.getAdapterTable().getValueAt(selected, 0).equals("Default")){
						overview.getBtnDelete().setEnabled(false);
					} else {
						overview.getBtnDelete().setEnabled(true);
					}
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

	public void saveOverview(String description) {
		patternModel.updatePattern(currentPattern, description);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("playDiagram")){
			DiagramModel model = (DiagramModel)evt.getNewValue();
			if (model == null){
				JOptionPane.showMessageDialog(overview, "The adapter you chose is not valid.\nIt cannot be played.");
			} else {
				Diagram diagram = model.getCurrentAdapter().getDiagram();
				playController.showView();
				playController.play(diagram);
			}
			return;
		}
	}
	
	public void playDiagram(String adapter){
		editorModel.loadDiagram(currentPattern, adapter);			
	}

	public void deleteAdapter(String adapter) {
		((DefaultTableModel)overview.getAdapterTable().getModel()).removeRow(overview.getAdapterTable().getSelectedRow());
		editorModel.deleteAdapter(currentPattern, adapter);
	}

	public void deletePattern() {
		int result = JOptionPane.showConfirmDialog(overview, "You are about to delete "+currentPattern+".\nAre you sure?");
		if (result == JOptionPane.OK_OPTION){
			((DefaultTableModel)overview.getTable().getModel()).removeRow(overview.getTable().getSelectedRow());
			patternModel.dbDelete(currentPattern);
			mainController.removeTab(currentPattern);
		}
	}

	public MainScreenController getMainController() {
		return mainController;
	}

	public void setMainController(MainScreenController mainController) {
		this.mainController = mainController;
	}
}

package controller;


import java.util.Locale;

import javax.swing.JComboBox;
import javax.swing.JTable;

import model.DiagramModel;
import model.PatternModel;
import view.MainScreen;
import view.dialog.PatternChooser;
import view.dialog.PatternDialog;

public class MainScreenController {
	
	private Locale currentLocale;

	private MainScreen view;
	private DiagramModel diagramModel;
	private PatternModel patternModel;
	private PatternOverviewController oveviewController;
	
	
	public MainScreenController(MainScreen mainScreen, DiagramModel diagramModel, PatternModel patternModel, PatternOverviewController patternOverviewController) {
		setCurrentLocale(Locale.ENGLISH);
		
		this.view = mainScreen;
		this.oveviewController = patternOverviewController;
		this.diagramModel = diagramModel;
		this.patternModel = patternModel;
	}
	
	public void createNewDiagramDialog(){
		PatternChooser dialog = new PatternChooser(this, "diagram", currentLocale);
		dialog.setVisible(true);		
	}
	
	public void createDiagram(String pattern){
		diagramModel.createDiagram(pattern);
	}
	
	public void createNewVersionDialog() {
		PatternChooser dialog = new PatternChooser(this, "version", currentLocale);
		
		dialog.setVisible(true);
	}
	
	public void createVersion(String pattern) {
		diagramModel.createVersion(pattern);
	}
	
	public void createNewAdapterDialog(){
		PatternChooser dialog = new PatternChooser(this, "adapter", currentLocale);
		dialog.setVisible(true);		
	}
	
	public void createAdapter(String pattern){
		diagramModel.createAdapter(pattern);
	}
	
	public void createNewPatternDialog() {
		PatternDialog dialog = new PatternDialog(this, "new", currentLocale);
		dialog.setVisible(true);
	}

	public void initCombobox(JComboBox<String> comboBox){
		for (String str : patternModel.getPatterns()) {
			comboBox.addItem(str);
		}
		if (comboBox.getItemCount()>0){
			comboBox.setSelectedIndex(0);			
		}
	}

	public DiagramModel getDiagramModel() {
		return diagramModel;
	}

	public void setDiagramModel(DiagramModel diagramModel) {
		this.diagramModel = diagramModel;
	}


	public void createNewPattern(String name, String desc) {
		patternModel.createPattern(name, desc);
	}

	public void updatePattern(String name, String desc) {
		// TODO Auto-generated method stub
				
	}

	public void showPatternOverview() {
		oveviewController.showPatternOverview();
	}

	public Locale getCurrentLocale() {
		return currentLocale;
	}

	public void setCurrentLocale(Locale currentLocale) {
		this.currentLocale = currentLocale;
	}

	public void populateTable(JTable table) {
		table.setModel(patternModel.generateTableModel());
	}
}

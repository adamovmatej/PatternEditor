package controller;


import javax.swing.JComboBox;

import controller.listener.MainFrameListener;
import model.DiagramModel;
import model.PatternModel;
import view.MainScreen;
import view.dialog.DiagramDialog;
import view.dialog.PatternDialog;

public class MainScreenController {

	private MainScreen view;
	private DiagramModel diagramModel;
	private PatternModel patternModel;
	
	
	public MainScreenController(MainScreen mainScreen, DiagramModel diagramModel, PatternModel patternModel) {
		this.view = mainScreen;
		view.addWindowListener(new MainFrameListener());
		this.diagramModel = diagramModel;
		this.patternModel = patternModel;
	}
	
	public void createNewDiagramDialog(){
		DiagramDialog dialog = new DiagramDialog(this, "diagram");
		dialog.setVisible(true);		
	}
	
	public void createDiagram(String name, String version, Boolean main){
		diagramModel.createDiagram(name, version, main);
	}
	
	public void createNewVersionDialog() {
		DiagramDialog dialog = new DiagramDialog(this, "version");
		dialog.getPatternComboBox().setEnabled(false);
		dialog.setVisible(true);
	}
	
	public void createNewVersion(String name, String version, Boolean main) {
		diagramModel.createVersion(name, version, main);
	}
	
	public void createNewPatternDialog() {
		PatternDialog dialog = new PatternDialog(this, "new");
		dialog.setVisible(true);
	}

	public void initCombobox(JComboBox<String> comboBox){
		for (String str : patternModel.getPatterns()) {
			comboBox.addItem(str);
		}
		comboBox.setSelectedIndex(0);
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
}

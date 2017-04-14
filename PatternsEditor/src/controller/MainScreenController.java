package controller;

import javax.swing.JComboBox;
import model.DiagramModel;
import model.Pattern;
import model.PatternModel;
import view.MainScreen;
import view.dialogs.NewDiagramDialog;
import view.dialogs.PatternDialog;

public class MainScreenController {

	private MainScreen view;
	private DiagramModel diagramModel;
	private PatternModel patternModel;
	
	
	public MainScreenController(MainScreen mainScreen, DiagramModel diagramModel, PatternModel patternModel) {
		this.view = mainScreen;
		this.diagramModel = diagramModel;
		this.patternModel = patternModel;
	}
	
	public void createNewDiagramDialog(){
		NewDiagramDialog dialog = new NewDiagramDialog(this, "diagram");
		dialog.setVisible(true);		
	}
	
	public void createDiagram(String name, String version, Boolean main){
		diagramModel.createDiagram(name, version, main);
	}
	
	public void createNewVersionDialog() {
		NewDiagramDialog dialog = new NewDiagramDialog(this, "version");
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

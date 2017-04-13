package controller;

import javax.swing.JComboBox;
import model.DiagramModel;
import view.MainScreen;
import view.dialogs.NewDiagramDialog;

public class MainScreenController {

	private MainScreen view;
	private DiagramModel diagramModel;
	
	
	public MainScreenController(MainScreen mainScreen, DiagramModel diagramModel) {
		this.view = mainScreen;
		this.diagramModel = diagramModel;
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
	
	public void initCombobox(JComboBox<String> comboBox){
		comboBox.addItem("Architect also implements");
		comboBox.addItem("Variation behind interface");
		comboBox.addItem("Stand up meetings");
		comboBox.addItem("Code ownership");
		comboBox.setSelectedIndex(0);
	}

	public DiagramModel getDiagramModel() {
		return diagramModel;
	}

	public void setDiagramModel(DiagramModel diagramModel) {
		this.diagramModel = diagramModel;
	}
}

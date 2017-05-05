package controller;


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.Locale;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import model.DiagramModel;
import model.EditorModel;
import model.PatternModel;
import view.MainScreen;
import view.ToolBarView;
import view.dialog.PatternChooser;
import view.dialog.PatternDialog;
import view.menu.MainMenuBar;

public class MainScreenController implements PropertyChangeListener{
	
	private Locale currentLocale;

	private MainScreen view;
	private MainMenuBar bar;
	private EditorModel editorModel;
	private PatternModel patternModel;
	private PatternOverviewController oveviewController;
	private EditorController editorController;
	private ToolBarView toolBar;
	
	
	public MainScreenController(MainScreen mainScreen, MainMenuBar bar, EditorModel editorModel, PatternModel patternModel, PatternOverviewController patternOverviewController, EditorController editorController, ToolBarView toolBar) {
		setCurrentLocale(Locale.ENGLISH);
		
		editorModel.addListener(this);
		this.toolBar = toolBar;
		this.view = mainScreen;
		this.bar = bar;
		this.oveviewController = patternOverviewController;
		this.editorController = editorController;
		this.editorModel = editorModel;
		this.patternModel = patternModel;
	}
	
	public void createNewDiagramDialog(){
		PatternChooser dialog = new PatternChooser(this, "diagram", currentLocale);
		dialog.setVisible(true);		
	}
	
	public void createDiagram(String pattern){
		editorModel.createDiagramModel(pattern);
	}
	
	public void createNewAdapterDialog(){
		PatternChooser dialog = new PatternChooser(this, "adapter", currentLocale);
		dialog.setVisible(true);		
	}
	
	public void createAdapter(List<String> patterns) {
		editorModel.createAdapter(patterns);
	}
	
	public void createOpenDiagramDialog() {
		PatternChooser dialog = new PatternChooser(this, "open", currentLocale);
		dialog.setVisible(true);
	}
	
	public void openDiagram(String pattern){
		if (editorModel.getDiagramModels().get(pattern)!=null){
			JOptionPane.showMessageDialog(view, "Model for this organizational pattern is alread openned.");
		} else {
			editorModel.open(pattern);
		}
	}
	
	public void createNewPatternDialog() {
		PatternDialog dialog = new PatternDialog(this, currentLocale);
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

	public void createNewPattern(String name, String desc) {
		patternModel.createPattern(name, desc);
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

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("diagramModelChange") || evt.getPropertyName().equals("newDiagramModel")){
			if (evt.getOldValue() == null){
				bar.chceckDiagramItems(false);
			} else {
				bar.chceckDiagramItems(true);
			}
			return;
		}
	}

	public void saveDiagram() {
		editorModel.save();
	}

	public void removeTab() {
		int result = JOptionPane.showConfirmDialog(view, "Do you want to save before closing?");
		if (result == JOptionPane.CANCEL_OPTION){
			return;
		}
		if (result == JOptionPane.OK_OPTION){
			saveDiagram();
		}
		editorController.removeTab();
	}

	public void saveAll() {
		editorModel.saveAll();
	}

	public void exit() {
		int result = JOptionPane.showConfirmDialog(view, "Do you want to save before exit?");
		if (result == JOptionPane.CANCEL_OPTION){
			return;
		}
		if (result == JOptionPane.OK_OPTION){
			saveAll();
		}
		view.dispose();
	}

	public void removeAllTabs() {
		int result = JOptionPane.showConfirmDialog(view, "Do you want to save before closing?");
		if (result == JOptionPane.CANCEL_OPTION){
			return;
		}
		if (result == JOptionPane.OK_OPTION){
			saveAll();
		}
		editorController.removeAllTabs();
	}

	public void showToolbar() {
		editorController.showToolBar();
	}
}

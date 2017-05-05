package view;

import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.Timer;

import controller.EditorController;

public class EditorView extends JSplitPane {

	private static final long serialVersionUID = 1L;
		
	private EditorController controller;
	
	private CustomTabPane map;
	private ToolBarView toolBarView;
	private JSplitPane rightPane;
	
	public EditorView(VersionPanelView versionPanelView, ToolBarView toolBarView) {
		this.setToolBarView(toolBarView);
		this.map = new CustomTabPane();	
		rightPane = new JSplitPane();
		rightPane.setOrientation(VERTICAL_SPLIT);
		rightPane.setLeftComponent(toolBarView);
		rightPane.setRightComponent(map);
		rightPane.setDividerSize(0);
		setRightComponent(rightPane);
		setLeftComponent(versionPanelView);
		resetDivider();
		setDividerSize(SOMEBITS);
	}
	
	public EditorController getController() {
		return controller;
	}

	public void setController(EditorController controller) {
		this.controller = controller;
		map.setController(controller);
	}
	
	public void showToolBar(){
		if (rightPane.getDividerLocation()==32){
			rightPane.setDividerLocation(1);
		} else {
			rightPane.setDividerLocation(32);
		}
	}

	public CustomTabPane getMap() {
		return map;
	}

	public void setMap(CustomTabPane map) {
		this.map = map;
	}
	
	public void resetDivider(){
		setDividerLocation(200);
	}

	public ToolBarView getToolBarView() {
		return toolBarView;
	}

	public void setToolBarView(ToolBarView toolBarView) {
		this.toolBarView = toolBarView;
	}

	public JSplitPane getRightPane() {
		return rightPane;
	}

	public void setRightPane(JSplitPane rightPane) {
		this.rightPane = rightPane;
	}
	
	
}

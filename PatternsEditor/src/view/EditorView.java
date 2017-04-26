package view;

import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import controller.EditorController;

public class EditorView extends JSplitPane {

	private static final long serialVersionUID = 1L;
		
	private EditorController controller;
	
	private CustomTabPane map;
	
	public EditorView(VersionPanelView versionPanelView) {		
		map = new CustomTabPane();		
		setRightComponent(map);
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

	public CustomTabPane getMap() {
		return map;
	}

	public void setMap(CustomTabPane map) {
		this.map = map;
	}
	
	public void resetDivider(){
		setDividerLocation(200);
	}
}

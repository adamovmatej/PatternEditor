package view;

import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import controller.EditorController;

public class EditorView extends JSplitPane {

	private static final long serialVersionUID = 1L;
		
	private EditorController controller;
	
	private JTabbedPane map;
	
	public EditorView(VersionPanelView versionPanelView) {		
		map = new JTabbedPane();
		
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
	}

	public JTabbedPane getMap() {
		return map;
	}

	public void setMap(JTabbedPane map) {
		this.map = map;
	}
	
	public void resetDivider(){
		setDividerLocation(200);
	}
}

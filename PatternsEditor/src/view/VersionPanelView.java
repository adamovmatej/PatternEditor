package view;

import javax.swing.JTable;

import controller.VersionPanelController;

import javax.swing.JSplitPane;
import javax.swing.JScrollPane;

public class VersionPanelView extends JSplitPane{
	
	private static final long serialVersionUID = 1L;
	
	private VersionPanelController controller;
	
	private JTable mainTable;
	private JTable versionTable;

	public VersionPanelView() {
		setOrientation(JSplitPane.VERTICAL_SPLIT);
				
		JScrollPane mainScrollPane = new JScrollPane();
		setLeftComponent(mainScrollPane);
		
		mainTable = new JTable();
		mainTable.setDefaultEditor(Object.class, null);
		mainScrollPane.setViewportView(mainTable);
		
		JScrollPane versionScrollPane = new JScrollPane();
		setRightComponent(versionScrollPane);
		
		versionTable = new JTable();
		versionTable.setDefaultEditor(Object.class, null);
		versionScrollPane.setViewportView(versionTable);

	}

	public JTable getMainTable() {
		return mainTable;
	}

	public void setMainTable(JTable mainTable) {
		this.mainTable = mainTable;
	}

	public JTable getVersionTable() {
		return versionTable;
	}

	public void setVersionTable(JTable versionTable) {
		this.versionTable = versionTable;
	}

	public VersionPanelController getController() {
		return controller;
	}

	public void setController(VersionPanelController controller) {
		this.controller = controller;
	}	
}

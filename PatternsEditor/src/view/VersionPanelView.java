package view;

import javax.swing.JTable;

import controller.VersionPanelController;

import javax.swing.JSplitPane;
import javax.swing.JScrollPane;

public class VersionPanelView extends JScrollPane {
	
	private static final long serialVersionUID = 1L;
	
	private VersionPanelController controller;
	
	private JTable table;

	public VersionPanelView() {		
		table = new JTable();
		table.setDefaultEditor(Object.class, null);
		setViewportView(table);

	}

	public JTable getAdapterTable() {
		return table;
	}

	public void setAdapterTable(JTable versionTable) {
		this.table = versionTable;
	}

	public VersionPanelController getController() {
		return controller;
	}

	public void setController(VersionPanelController controller) {
		this.controller = controller;
	}	
}

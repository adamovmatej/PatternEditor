package model;

import java.util.Map;

import javax.swing.table.DefaultTableModel;

public class VersionModel {	
	
	private DefaultTableModel mainTableModel;
	private DefaultTableModel secondaryTableModel;
	
	public VersionModel(Diagram diagram) {
		initTables(diagram);
	}
	
	private void initTables(Diagram diagram){
		mainTableModel = new DefaultTableModel(new Object[]{diagram.getPattern()},0);
		secondaryTableModel = new DefaultTableModel(new Object[]{"Versions:"},0);
		Map<String, Version> map = diagram.getVersions();
		for (String key : map.keySet()) {
			addVersion(map.get(key));
		}
	}
	
	public void addVersion(Version version){
		if (version.getMain()){
			mainTableModel.addRow(new Object[]{version.getVersion()});
		} else {
			secondaryTableModel.addRow(new Object[]{version.getVersion()});
		}
	}
	
	public DefaultTableModel getMainTableModel() {
		return mainTableModel;
	}
	public void setMainTableModel(DefaultTableModel mainTableModel) {
		this.mainTableModel = mainTableModel;
	}
	public DefaultTableModel getSecondaryTableModel() {
		return secondaryTableModel;
	}
	public void setSecondaryTableModel(DefaultTableModel secondaryTableModel) {
		this.secondaryTableModel = secondaryTableModel;
	}

}

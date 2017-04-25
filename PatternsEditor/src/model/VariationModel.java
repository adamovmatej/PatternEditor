package model;

import java.util.Map;

import javax.swing.table.DefaultTableModel;

public class VariationModel {	
	
	private DefaultTableModel versionTableModel;
	private DefaultTableModel adapterTableModel;
	
	public VariationModel(Diagram diagram) {
		initTables(diagram);
	}
	
	private void initTables(Diagram diagram){
		versionTableModel = new DefaultTableModel(new Object[]{"Versions:"},0);
		adapterTableModel = new DefaultTableModel(new Object[]{"Adapters:"},0);
		Map<String, Version> versionMap = diagram.getVersions();
		for (String key : versionMap.keySet()) {
			versionTableModel.addRow(new Object[]{key});
		}
		Map<String, Adapter> adapterMap = diagram.getAdapters();
		for (String key : adapterMap.keySet()) {
			adapterTableModel.addRow(new Object[]{key});
		}
	}
	
	public DefaultTableModel getMainTableModel() {
		return versionTableModel;
	}
	public void setMainTableModel(DefaultTableModel mainTableModel) {
		this.versionTableModel = mainTableModel;
	}
	public DefaultTableModel getSecondaryTableModel() {
		return adapterTableModel;
	}
	public void setSecondaryTableModel(DefaultTableModel secondaryTableModel) {
		this.adapterTableModel = secondaryTableModel;
	}
	
	public void addVariation(Variation variation){
		if (variation.getClass().equals(Version.class)){
			versionTableModel.addRow(new Object[]{variation.getSecondaryPattern()});			
		} else {
			adapterTableModel.addRow(new Object[]{variation.getSecondaryPattern()});	
		}
	}

}

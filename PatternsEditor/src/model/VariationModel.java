package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import javax.swing.table.DefaultTableModel;

import model.db.SQLConnection;

public class VariationModel {	
	
	private DefaultTableModel versionTableModel;
	private DefaultTableModel adapterTableModel;
	
	public VariationModel(Diagram diagram) {
		initTables(diagram);
	}
	
	public VariationModel(String pattern, Connection connection){
		initTables(pattern, connection);
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
	
	private void initTables(String pattern, Connection connection){
		versionTableModel = new DefaultTableModel(new Object[]{"Versions:"},0);
		adapterTableModel = new DefaultTableModel(new Object[]{"Adapters:"},0);
		
		String sqlVer = "SELECT secondaryPattern FROM version WHERE mainPattern = ?";
		String sqlAda = "SELECT secondaryPattern FROM adapter WHERE mainPattern = ?";
		Connection con = connection;				
		if (connection == null){
			con = SQLConnection.getInstance().getConnection();
		} else {
			con = connection;
		}
        try (PreparedStatement pstmtVer = con.prepareStatement(sqlVer); PreparedStatement pstmtAda = con.prepareStatement(sqlAda)) {
        	con.setAutoCommit(false);
            pstmtVer.setString(1, pattern);
            ResultSet rs1 = pstmtVer.executeQuery();
            while (rs1.next()){
            	versionTableModel.addRow(new Object[]{rs1.getString("secondaryPattern")});
            }
            pstmtAda.setString(1, pattern);
            ResultSet rs2 = pstmtAda.executeQuery();
            while (rs2.next()){
            	adapterTableModel.addRow(new Object[]{rs2.getString("secondaryPattern")});
            }
        } catch (SQLException e) {
        	System.out.println(sqlVer);
            System.out.println(e.getMessage());
        } finally {
        	try {
        		if (con != null){
        			con.commit();
        			if (connection == null){
        				con.close();
        			}
        		}
			} catch (SQLException e) {
				e.printStackTrace();
			}
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

package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import javax.swing.table.DefaultTableModel;

import model.db.SQLConnection;

public class AdapterModel {	
	
	private DefaultTableModel adapterTableModel;
	
	public AdapterModel(DiagramModel model) {
		initTables(model);
	}
	
	public AdapterModel(String pattern, Connection connection){
		initTables(pattern, connection);
	}
	
	private void initTables(DiagramModel model){
		adapterTableModel = new DefaultTableModel(new Object[]{"Adapters:"},0);		
		Map<String, Adapter> adapterMap = model.getAdapters();
		for (String key : adapterMap.keySet()) {
			adapterTableModel.addRow(new Object[]{model.getAdapter(key).getLineName()});
		}
	}
	
	private void initTables(String pattern, Connection connection){
		adapterTableModel = new DefaultTableModel(new Object[]{"Adapters:"},0);
		
		String sqlAda = "SELECT secondaryPattern FROM adapter WHERE mainPattern = ?";
		Connection con = connection;				
		if (connection == null){
			con = SQLConnection.getInstance().getConnection();
		} else {
			con = connection;
		}
        try (PreparedStatement pstmtAda = con.prepareStatement(sqlAda)) {
        	con.setAutoCommit(false);
           
            pstmtAda.setString(1, pattern);
            ResultSet rs2 = pstmtAda.executeQuery();
            while (rs2.next()){
            	adapterTableModel.addRow(new Object[]{rs2.getString("secondaryPattern")});
            }
        } catch (SQLException e) {
        	System.out.println(sqlAda);
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
	public DefaultTableModel getAdapterTableModel() {
		return adapterTableModel;
	}
	public void setAdapterTableModel(DefaultTableModel secondaryTableModel) {
		this.adapterTableModel = secondaryTableModel;
	}
	
	public void addAdapter(String name){
		adapterTableModel.addRow(new Object[]{name});
	}

}

package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.swing.table.DefaultTableModel;

import model.db.SQLConnection;

public class AdapterModel extends DefaultTableModel{	
	
	private static final long serialVersionUID = 1L;
	
	public AdapterModel() {
		super(new Object[]{"Adapters:"},0);		
	}
	
	public void initTables(DiagramModel model) {
		Map<String, Adapter> adapterMap = model.getAdapters();
		this.addRow(new Object[]{model.getAdapter("<html>Default</html>").getLineName()});
		for (String key : adapterMap.keySet()) {
			if (!key.equals("<html>Default</html>")){
				this.addRow(new Object[]{model.getAdapter(key).getLineName()});
			}
		}
	}
	
	public void initTables(String pattern, Connection connection){
		this.addRow(new Object[]{"Default"});
		String sqlAda = "SELECT name FROM adapter WHERE pattern = ?";
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
            	System.out.println(rs2.getString("name"));
            	this.addRow(new Object[]{rs2.getString("name")});
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
	
	public void addAdapter(String name){
		this.addRow(new Object[]{name});
	}
	
	private String getLineName(String name){
		String result="<html>";
		List<String> items = Arrays.asList(name.split("\\s*/\\s*"));
		for (String string : items) {
			result += string + "<br>";
		}
		return result.substring(0, result.length()-4)+"</html>";
	}

	public void removeRow(Adapter adapter) {
		for (int i = 0; i < this.getRowCount(); i++) {
			adapter.getLineName();
			if (this.getValueAt(i, 0).equals(adapter.getLineName())){
				this.removeRow(i);
				return;
			}
		}
	}
}

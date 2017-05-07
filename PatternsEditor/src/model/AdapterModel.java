package model;

import java.sql.Connection;
import java.util.Map;

import javax.swing.table.DefaultTableModel;

import model.db.AdapterDAO;
import model.db.DAOFactory;

public class AdapterModel extends DefaultTableModel{	
	
	private static final long serialVersionUID = 1L;
	private AdapterDAO adapterDAO;
	
	public AdapterModel() {
		super(new Object[]{"Adapters:"},0);		
		this.adapterDAO = DAOFactory.getInstance().getAdapterDAO();
	}
	
	public void initTables(PatternModel model) {
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
		for (String row : adapterDAO.getAdapters(pattern)) {
			this.addRow(new Object[]{row});
		}
	}
	
	public void addAdapter(String name){
		this.addRow(new Object[]{name});
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

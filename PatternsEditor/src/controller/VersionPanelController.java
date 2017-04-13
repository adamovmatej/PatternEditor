package controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.TableView.TableRow;

import model.Diagram;
import model.DiagramModel;
import model.Version;
import model.VersionModel;
import view.VersionPanelView;

public class VersionPanelController implements PropertyChangeListener{
	
	private VersionPanelView view;
	private DiagramModel model;
	
	public VersionPanelController(VersionPanelView view, DiagramModel model) {
		this.view = view;	
		this.model = model;
		
		initTables();
		
		model.addListener(this);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("newDiagram")){
			Diagram diagram = (Diagram) evt.getNewValue();
			VersionModel versionModel = (VersionModel) evt.getOldValue();
			view.getMainTable().setModel(versionModel.getMainTableModel());
			view.getVersionTable().setModel(versionModel.getSecondaryTableModel());
			selectTableRow(diagram.getCurrentVersion());
			return;
		}
		if (evt.getPropertyName().equals("newVersion")){
			Version version = (Version) evt.getNewValue();
			selectTableRow(version);
			return;
		}
	}
	
	private void selectTableRow(Version version){
		if (version.getMain()){
			for (int i=0; i<view.getMainTable().getRowCount(); i++){
				if (version.getVersion().equals(view.getMainTable().getValueAt(i, 0))){
					view.getMainTable().setRowSelectionInterval(i, i);
					//view.getVersionTable().clearSelection();
					return;
				}
			}
		} else {
			for (int i=0; i<view.getVersionTable().getRowCount(); i++){
				if (version.getVersion().equals(view.getVersionTable().getValueAt(i, 0))){
					view.getVersionTable().setRowSelectionInterval(i, i);
					//view.getMainTable().clearSelection();
					return;
				}
			}
		}
	}
	
	private void initTables(){
		view.getMainTable().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		view.getVersionTable().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		view.getMainTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (view.getMainTable().getSelectedRow()!=-1){
					view.getVersionTable().clearSelection();
					model.changeVersion((String) view.getMainTable().getModel().getValueAt(view.getMainTable().getSelectedRow(), 0));					
				}
			}
		});
		
		view.getVersionTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (view.getVersionTable().getSelectedRow()!=-1){
					view.getMainTable().clearSelection();
					model.changeVersion((String) view.getVersionTable().getModel().getValueAt(view.getVersionTable().getSelectedRow(), 0));
				}	
			}
		});
	}
}

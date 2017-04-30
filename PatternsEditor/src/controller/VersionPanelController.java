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
import model.EditorModel;
import model.Variation;
import model.Version;
import model.Adapter;
import model.AdapterModel;
import view.VersionPanelView;

public class VersionPanelController implements PropertyChangeListener{
	
	private VersionPanelView view;
	private EditorModel model;
	
	public VersionPanelController(VersionPanelView view, EditorModel editorModel) {
		this.view = view;
		this.model = editorModel;
		
		initTables();
		
		editorModel.addListener(this);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("newDiagramModel") || evt.getPropertyName().equals("diagramModelChange")){
			DiagramModel diagramModel = (DiagramModel) evt.getNewValue();
			AdapterModel versionModel = (AdapterModel) evt.getOldValue();
			if (versionModel != null){
				view.getAdapterTable().setModel(versionModel.getAdapterTableModel());
				selectTableRow(diagramModel.getCurrentAdapter().getLineName());
			} else {
				view.getAdapterTable().setModel(new DefaultTableModel());
			}
			return;
		}
		if (evt.getPropertyName().equals("newAdapter")){
			String name = ((Adapter) evt.getNewValue()).getLineName();
			selectTableRow(name);
			return;
		}
		if (evt.getPropertyName().equals("changeDiagram")){
			AdapterModel variationModel = (AdapterModel) evt.getNewValue();
			if (variationModel == null){
				view.getAdapterTable().setModel(new DefaultTableModel());
			} else {
				view.getAdapterTable().setModel(variationModel.getAdapterTableModel());
			}
			return;
		}
	}
	
	private void selectTableRow(String name){		
		for (int i=0; i<view.getAdapterTable().getRowCount(); i++){
			if (name.equals(view.getAdapterTable().getValueAt(i, 0))){
				view.getAdapterTable().setRowSelectionInterval(i, i);
				return;
			}
		}
	}
	
	private void initTables(){
		view.getAdapterTable().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		view.getAdapterTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (view.getAdapterTable().getSelectedRow()!=-1){
					model.getCurrentDiagramModel().changeAdapter((String) view.getAdapterTable().getModel().getValueAt(view.getAdapterTable().getSelectedRow(), 0));
				}	
			}
		});
	}
}

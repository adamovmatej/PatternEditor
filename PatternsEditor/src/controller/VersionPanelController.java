package controller;

import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.TableView.TableRow;

import model.Diagram;
import model.PatternModel;
import model.EditorModel;
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
			PatternModel diagramModel = (PatternModel) evt.getNewValue();
			AdapterModel adapterModel = (AdapterModel) evt.getOldValue();
			if (adapterModel != null){
				view.getAdapterTable().setModel(adapterModel);
				selectTableRow(diagramModel.getCurrentAdapter().getLineName());
			} else {
				view.getAdapterTable().setModel(new DefaultTableModel());
			}
			updateTableRowHeights();
			return;
		}
		if (evt.getPropertyName().equals("newAdapter")){
			if (evt.getNewValue()!=null){
				String name = ((Adapter) evt.getNewValue()).getLineName();
				selectTableRow(name);				
			}
			return;
		}
		if (evt.getPropertyName().equals("changeDiagram")){
			AdapterModel variationModel = (AdapterModel) evt.getNewValue();
			if (variationModel == null){
				view.getAdapterTable().setModel(new DefaultTableModel());
			} else {
				view.getAdapterTable().setModel(variationModel);
			}
			return;
		}
		if (evt.getPropertyName().equals("tableChange")){
			updateTableRowHeights();
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
	
	public void updateTableRowHeights(){
		JTable table = view.getAdapterTable();
		for (int i = 0; i < table.getRowCount(); i++){
			int rowHeight = table.getRowHeight();
			Component comp = table.prepareRenderer(table.getCellRenderer(i, 0), i, 0);
			rowHeight = Math.max(rowHeight, comp.getPreferredSize().height);
			table.setRowHeight(i, rowHeight);
		}
		table.repaint();
	}
}

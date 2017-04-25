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
import model.Variation;
import model.Version;
import model.VariationModel;
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
			VariationModel versionModel = (VariationModel) evt.getOldValue();
			view.getVersionTable().setModel(versionModel.getMainTableModel());
			view.getAdapterTable().setModel(versionModel.getSecondaryTableModel());
			selectTableRow(diagram.getCurrentVariation());
			return;
		}
		if (evt.getPropertyName().equals("newVariation")){
			Variation variation = (Variation) evt.getNewValue();
			selectTableRow(variation);
			return;
		}
		if (evt.getPropertyName().equals("changeDiagram")){
			VariationModel versionModel = (VariationModel) evt.getNewValue();
			view.getVersionTable().setModel(versionModel.getMainTableModel());
			view.getAdapterTable().setModel(versionModel.getSecondaryTableModel());
			return;
		}
	}
	
	private void selectTableRow(Variation variation){
		if (variation.getClass().equals(Version.class)){
			for (int i=0; i<view.getVersionTable().getRowCount(); i++){
				if (variation.getSecondaryPattern().equals(view.getVersionTable().getValueAt(i, 0))){
					view.getVersionTable().setRowSelectionInterval(i, i);
					return;
				}
			}
		} else {
			for (int i=0; i<view.getAdapterTable().getRowCount(); i++){
				if (variation.getSecondaryPattern().equals(view.getAdapterTable().getValueAt(i, 0))){
					view.getAdapterTable().setRowSelectionInterval(i, i);
					return;
				}
			}
		}
	}
	
	private void initTables(){
		view.getVersionTable().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		view.getAdapterTable().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		view.getVersionTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (view.getVersionTable().getSelectedRow()!=-1){
					view.getAdapterTable().clearSelection();
					model.changeVersion((String) view.getVersionTable().getModel().getValueAt(view.getVersionTable().getSelectedRow(), 0));					
				}
			}
		});
		
		view.getAdapterTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (view.getAdapterTable().getSelectedRow()!=-1){
					view.getVersionTable().clearSelection();
					model.changeAdapter((String) view.getAdapterTable().getModel().getValueAt(view.getAdapterTable().getSelectedRow(), 0));
				}	
			}
		});
	}
}

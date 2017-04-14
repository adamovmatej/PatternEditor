package controller;

import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JOptionPane;
import com.mxgraph.model.mxCell;
import com.mxgraph.view.mxGraph;

import listeners.ClickMapListener;
import listeners.KeyMapListener;
import model.Diagram;
import model.DiagramModel;
import model.State;
import view.EditorView;
import view.VersionPanelView;
import view.dialogs.StatePropertieDialog;
import view.menu.RightClickMapMenu;
import view.menu.RightClickStateMenu;

public class EditorController implements PropertyChangeListener{
	
	private EditorView view;
	private DiagramModel model;

	public EditorController(EditorView view, DiagramModel model) {
		this.view = view;
		this.model = model;
		
		model.addListener(this);
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("newDiagram")){
			Diagram diagram = (Diagram)evt.getNewValue();
			initListeners(diagram);
			insertDiagram(diagram);
			return;
		}
	}
	
	public void createStatePropertiesDialog(MouseEvent me){
		Diagram diagram = (Diagram)(view.getMap().getSelectedComponent());
		State state = (State) ((mxCell)(diagram.getCellAt(me.getX(), me.getY()))).getValue();
		StatePropertieDialog dialog = new StatePropertieDialog(this, state.getName(diagram.getCurrentVersion().getVersion()), state.getScene(diagram.getCurrentVersion().getVersion()), false, me);
		dialog.setVisible(true);
	}
	
	public void createNewStateDialog(MouseEvent me) {
		StatePropertieDialog dialog = new StatePropertieDialog(this, null, null, true, me);
		dialog.setVisible(true);
	}
	
	public void createState(String name, String scene, MouseEvent me) {
		Diagram diagram = (Diagram)view.getMap().getSelectedComponent();
		mxGraph graph = diagram.getGraph();
		State state = new State(name, scene, diagram.getCurrentVersion().getVersion(), diagram);
		
		graph.getModel().beginUpdate();
		try{
			graph.insertVertex(graph.getDefaultParent(), null, state, me.getX(), me.getY(), 100, 50);
		} finally{
			graph.getModel().endUpdate();
		}				
	}
	
	public void updateState(String name, String scene, MouseEvent me){
		Diagram diagram = (Diagram)view.getMap().getSelectedComponent();
		mxGraph graph = diagram.getGraph();
		
		graph.getModel().beginUpdate();
		try{
			mxCell cell = (mxCell) (((Diagram) (view.getMap().getSelectedComponent())).getCellAt(me.getX(), me.getY()));
			((State) cell.getValue()).update(name, scene, diagram.getCurrentVersion().getVersion());
		} finally{
			graph.getModel().endUpdate();
		}
		
		graph.refresh();
		graph.repaint();
	}
	
	public void rightClick(MouseEvent e) {
		Diagram diagram = (Diagram) view.getMap().getSelectedComponent();

		if (diagram.getGraph().getModel().isVertex(diagram.getCellAt(e.getX(), e.getY()))){
			RightClickStateMenu menu = new RightClickStateMenu(this, e);
			menu.show(e.getComponent(), e.getX(), e.getY());
		} else {
			RightClickMapMenu menu = new RightClickMapMenu(this, e);
			menu.show(e.getComponent(), e.getX(), e.getY());
		}
	}
	
	private void insertDiagram(Diagram diagram){
		view.getMap().add(diagram.getPattern(), diagram);
		view.getMap().setSelectedComponent(diagram);
	}
		
	private void initListeners(Diagram diagram) {
		diagram.getGraphControl().addMouseListener(new ClickMapListener(this));
		diagram.addKeyListener(new KeyMapListener(this));
		//diagram.addMouseWheelListener(new MouseWheelMapListener(this));
	}

	public void createDeleteDialog() {
		Diagram diagram = (Diagram) view.getMap().getSelectedComponent();
		Object[] selection = (diagram).getGraph().getSelectionModel().getCells();
	
		if (selection.length > 0){
			String msg = "Are you sure you want to delete this element?";
			if (selection.length > 1){
				msg = "Are you sure you want to delete these elements?";
			}
			int result = JOptionPane.showConfirmDialog(view, msg, "alert", JOptionPane.OK_CANCEL_OPTION);
			if (result == JOptionPane.YES_OPTION){
				diagram.getGraph().removeCells(selection);
			}			
		}
	}
	
	private void showModel(String pattern){
		
	}

	
}

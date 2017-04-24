package controller;

import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.mxgraph.model.mxCell;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;

import controller.listener.ClickMapListener;
import controller.listener.KeyMapListener;
import controller.listener.MouseWheelListener;
import model.Diagram;
import model.DiagramModel;
import model.Edge;
import model.State;
import view.EditorView;
import view.VersionPanelView;
import view.dialog.EdgePropertiesDialog;
import view.dialog.StatePropertiesDialog;
import view.menu.RightClickMapMenu;
import view.menu.RightClickCellMenu;

public class EditorController implements PropertyChangeListener{
	
	private EditorView view;
	private DiagramModel model;

	public EditorController(EditorView view, DiagramModel model) {
		this.view = view;
		this.model = model;
		((JTabbedPane)view.getRightComponent()).addChangeListener(new ChangeListener() {			
			@Override
			public void stateChanged(ChangeEvent e) {
				model.setCurrentDiagram((Diagram) view.getMap().getComponentAt(view.getMap().getSelectedIndex()));
			}
		});
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
		if (evt.getPropertyName().equals("newVersion")){
			repaintCells(model.getCurrentDiagram());
			view.getRightComponent().repaint();
			return;
		}
		if (evt.getPropertyName().equals("versionChange")){
			repaintCells(model.getCurrentDiagram());
			view.getRightComponent().repaint();
			return;
		}
	}
	
	public void createPropertiesDialog(MouseEvent me){
		Diagram diagram = model.getCurrentDiagram();
		mxCell cell = (mxCell)(diagram.getCellAt(me.getX(), me.getY()));
		if (cell.isEdge()){
			Object obj = cell.getValue();
			if (obj.getClass() == String.class){
				cell.setValue(new Edge(diagram.getCurrentVersion().getVersion()));
			}
			Edge edge = (Edge) cell.getValue();
			String version = diagram.getCurrentVersion().getVersion();
			EdgePropertiesDialog dialog = new EdgePropertiesDialog(this, edge.getName(version), edge.getScene(version), me);
			dialog.setVisible(true);
		} else{
			State state = (State) cell.getValue();
			String version = diagram.getCurrentVersion().getVersion();
			StatePropertiesDialog dialog = new StatePropertiesDialog(this, state.getName(version), state.getScene(version),state.getDisable(version), false, me);
			dialog.setVisible(true);
		}
		
	}
	
	public void createStateDialog(MouseEvent me) {
		StatePropertiesDialog dialog = new StatePropertiesDialog(this, null, null, false, true, me);
		dialog.setVisible(true);
	}
	
	public void createState(String name, String scene, Boolean disable, MouseEvent me) {
		Diagram diagram = (Diagram)view.getMap().getSelectedComponent();
		mxGraph graph = diagram.getGraph();
		State state = new State(name, scene, disable, diagram.getCurrentVersion().getVersion());
		model.addListener(state);
		
		graph.getModel().beginUpdate();
		try{
			mxCell cell = (mxCell) graph.insertVertex(graph.getDefaultParent(), null, state, me.getX(), me.getY(), 100, 50);
			paintCell(cell, diagram.getCurrentVersion().getVersion());
		} finally{
			graph.getModel().endUpdate();
		}			

		graph.refresh();
		graph.repaint();
	}
	
	public void updateState(String name, String scene, Boolean disable, MouseEvent me){
		Diagram diagram = (Diagram)view.getMap().getSelectedComponent();
		mxGraph graph = diagram.getGraph();
		
		graph.getModel().beginUpdate();
		try{
			mxCell cell = (mxCell) (((Diagram) (view.getMap().getSelectedComponent())).getCellAt(me.getX(), me.getY()));
			State state = (State) cell.getValue();
			if (state == null){
				state = new State();
			}
			state.update(name, scene, diagram.getCurrentVersion().getVersion(), disable);
			paintCell(cell, diagram.getCurrentVersion().getVersion());
		} finally{
			graph.getModel().endUpdate();
		}
		
		graph.refresh();
		graph.repaint();
	}
	
	private void paintCell(mxCell cell, String version){
		if (((State)cell.getValue()).getDisable(version)){
			cell.setStyle("defaultVertex;fillColor=gray");
		} else {
			cell.setStyle("defaultVertex");
		}
	}
	
	private void repaintCells(Diagram diagram){
		for (Object cell : diagram.getGraph().getChildVertices(diagram.getGraph().getDefaultParent())) {
			paintCell((mxCell) cell, diagram.getCurrentVersion().getVersion());
		}
	}
	
	public void rightClick(MouseEvent e) {
		Diagram diagram = (Diagram) view.getMap().getSelectedComponent();

		if ((diagram.getGraph().getModel().isVertex(diagram.getCellAt(e.getX(), e.getY()))) || (diagram.getGraph().getModel().isEdge(diagram.getCellAt(e.getX(), e.getY())))){
			RightClickCellMenu menu = new RightClickCellMenu(this, e);
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
		diagram.addMouseWheelListener(new MouseWheelListener(diagram));
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

	public void createEdgePropertiesDialog(MouseEvent me) {
		Diagram diagram = model.getCurrentDiagram();
		
		State state = (State) ((mxCell)(diagram.getCellAt(me.getX(), me.getY()))).getValue();
		String version = diagram.getCurrentVersion().getVersion();
		StatePropertiesDialog dialog = new StatePropertiesDialog(this, state.getName(version), state.getScene(version),state.getDisable(version), false, me);
		dialog.setVisible(true);
	}

	public void updateEdge(String name, String scene, MouseEvent me) {
		Diagram diagram = (Diagram)view.getMap().getSelectedComponent();
		mxGraph graph = diagram.getGraph();
		
		graph.getModel().beginUpdate();
		try{
			mxCell cell = (mxCell) (((Diagram) (view.getMap().getSelectedComponent())).getCellAt(me.getX(), me.getY()));
			Edge edge = (Edge) cell.getValue();
			edge.update(name, scene, diagram.getCurrentVersion().getVersion());
		} finally{
			graph.getModel().endUpdate();
		}
		
		graph.refresh();
		graph.repaint();
	}	
}
